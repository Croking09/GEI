package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Producto insertarProducto(String nombre, String descripcion, int duracionPuja, double precioSalida,
                                     String informacionEnvio, Long categoriaId, Long publicadorId)
            throws InstanceNotFoundException {

        User publicador = permissionChecker.checkUser(publicadorId);
        Optional<Categoria> categoria = categoriaDao.findById(categoriaId);

        if (!categoria.isPresent()) {
            throw new InstanceNotFoundException("project.entities.categoria", categoriaId);
        }

        LocalDateTime fechaFinPuja = LocalDateTime.now().plusMinutes(duracionPuja).withNano(0);
        Producto producto = new Producto(nombre, descripcion, LocalDateTime.now().withNano(0),
                fechaFinPuja, precioSalida, precioSalida, informacionEnvio, categoria.get(), publicador);
        productoDao.save(producto);

        return producto;
    }

    @Override
    public Block<Producto> verProductosPorUsuario(Long userId, int page, int size) throws InstanceNotFoundException {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", userId));

        Pageable pageable = PageRequest.of(page, size);
        Slice<Producto> productoSlice = productoDao.findByPublicadorOrderByFechaFinPujaDesc(user, pageable);

        return new Block<>(productoSlice.getContent(), productoSlice.hasNext());
    }

    @Override
    public Producto buscarProducto(Long id) throws InstanceNotFoundException {
        Optional<Producto> productoOptional = productoDao.findById(id);
        if(productoOptional.isEmpty()) {
            throw new InstanceNotFoundException("project.entities.producto", id);
        }
        return productoOptional.get();
    }

}
