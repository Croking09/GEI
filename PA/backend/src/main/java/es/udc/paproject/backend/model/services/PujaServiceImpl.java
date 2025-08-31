package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PujaFueraDePlazoException;
import es.udc.paproject.backend.model.exceptions.PujaUsuarioPublicadorException;
import es.udc.paproject.backend.model.exceptions.PujaValorNoValidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PujaServiceImpl implements PujaService {

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private PujaDao pujaDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductoDao productoDao;

    @Override
    public Puja insertarPuja(Double valor, Long productoId, Long userId)
            throws
            InstanceNotFoundException,
            PujaFueraDePlazoException,
            PujaUsuarioPublicadorException,
            PujaValorNoValidoException {
        // Comprobar que el usuario existe y obtenerlo:
        User user = permissionChecker.checkUser(userId);

        // Obtener el producto si existe:
        Producto producto;
        Optional<Producto> productoOptional = productoDao.findById(productoId);
        if (productoOptional.isEmpty())
            throw new InstanceNotFoundException("project.entities.producto", productoId);
        else
            producto = productoOptional.get();

        // Validar que el usuario que puja no es el publicador del producto:
        if (Objects.equals(userId, producto.getPublicador().getId()))
            throw new PujaUsuarioPublicadorException();

        // Validar que la puja se realiza dentro del plazo:
        if (producto.getFechaFinPuja().isBefore(LocalDateTime.now()))
            throw new PujaFueraDePlazoException();

        //Validar que el valor de la puja es válido:
        if (valor != null) {
            if (producto.tienePujas()) {
                if (valor <= producto.getValorActual())
                    throw new PujaValorNoValidoException(valor, productoId, producto.getValorActual());
            } else if (valor < producto.getValorActual())
                throw new PujaValorNoValidoException(valor, productoId, producto.getValorActual());
        }
        // Creamos y añadimos la puja:
        Puja puja = new Puja (user, producto, valor);

        // Comprobar que esta es la primera puja que se realiza:
        if(producto.getPujaGanadora() == null) {
            producto.setPujaGanadora(puja);
        } else {
            // Obtener la puja más alta actual para el producto:
            Puja pujaMaxActual = producto.getPujaGanadora();

            // Si ya existen pujas -> Aplicar incremento:
            if (pujaMaxActual != null) {

                // Aplicar incremento al valor actual del producto:
                if (Objects.equals(pujaMaxActual.getValor(), valor)) { // Puja máxima actual = Nueva puja
                    producto.setValorActual(pujaMaxActual.getValor());
                } else if (pujaMaxActual.getValor() > valor) { // Puja máxima actual > Nueva puja
                    if (valor + 0.5 <= pujaMaxActual.getValor()) {
                        producto.setValorActual(valor + 0.5);
                    }
                } else if (pujaMaxActual.getValor() < valor) { // Puja máxima actual < Nueva puja
                    if (valor >= pujaMaxActual.getValor() + 0.5) {
                        producto.setValorActual(pujaMaxActual.getValor() + 0.5);
                    }
                    producto.setPujaGanadora(puja);
                }
            } else {
                producto.setPujaGanadora(puja);
            }
        }

        pujaDao.save(puja);
        productoDao.save(producto);

        return puja;
    }

    @Override
    public Block<Puja> getPujasByUser(Long userId, int page, int size) throws InstanceNotFoundException {

        User user = userDao.findById(userId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", userId));

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        Page<Puja> pujaPage = pujaDao.findByUserOrderByFechaDesc(user, pageable);
        return new Block<>(pujaPage.getContent(), pujaPage.hasNext());
    }

}
