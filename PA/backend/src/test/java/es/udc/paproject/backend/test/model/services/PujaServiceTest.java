package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ProductoService;
import es.udc.paproject.backend.model.services.UserService;
import es.udc.paproject.backend.model.services.PujaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PujaServiceTest {

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private PujaDao pujaDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UserService userService;

    @Autowired
    private PujaService pujaService;

    private User signUpUser(String userName) {

        User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");

        try {
            userService.signUp(user);
        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

        return user;

    }

    private Categoria addCategoria(String name) {
        return categoriaDao.save(new Categoria(name));
    }

    private Producto addProducto(Long categoryId, Long userId) throws InstanceNotFoundException {
        return productoService.insertarProducto(
                "Toyota Hilux",
                "Automóvil",
                30,
                10,
                "Se entrega en concesionario local",
                categoryId,
                userId
        );
    }

    @Test
    public void testPujaCorrecta() throws InstanceNotFoundException, PujaUsuarioPublicadorException, PujaFueraDePlazoException, PujaValorNoValidoException {
        // Usamos como base los ejemplos del enunciado.
        Puja puja;
        Producto prod;

        // Insertamos un producto:
        User user = signUpUser("vendedor"); // Usuario que publica el producto.
        Categoria categoria = addCategoria("Vehículos"); // Categoría del producto.
        Producto producto = addProducto(categoria.getId(), user.getId()); // Producto.

        // Creamos los usuarios que van a pujar y sus pujas:
        User compradorA = signUpUser("compradorA");
        User compradorB = signUpUser("compradorB");
        User compradorC = signUpUser("compradorC");
        User compradorD = signUpUser("compradorD");

        Puja pujaA;
        Puja pujaB;
        Puja pujaC;
        Puja pujaD;

        Puja pujaGanadoraActual;

        // compradorA puja con 12 Euros:
        pujaA = pujaService.insertarPuja(12D, producto.getId(), compradorA.getId());
        puja = pujaDao.findById(pujaA.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.puja", pujaA.getId()));
        compararPujas(puja, pujaA);
        pujaGanadoraActual = puja;
        prod = productoDao.findById(producto.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.producto", producto.getId()));
        compararProductoValorYPujaGanadora(prod, 10D, pujaGanadoraActual);

        // compradorB puja con 11 Euros:
        pujaB = pujaService.insertarPuja(11D, producto.getId(), compradorB.getId());
        puja = pujaDao.findById(pujaB.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.puja", pujaB.getId()));
        compararPujas(puja, pujaB);
        prod = productoDao.findById(producto.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.producto", producto.getId()));
        compararProductoValorYPujaGanadora(prod, 11.5, pujaGanadoraActual);

        // compradorC puja con 14 Euros:
        pujaC = pujaService.insertarPuja(14D, producto.getId(), compradorC.getId());
        puja = pujaDao.findById(pujaC.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.puja", pujaC.getId()));
        compararPujas(puja, pujaC);
        pujaGanadoraActual = puja;
        prod = productoDao.findById(producto.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.producto", producto.getId()));
        compararProductoValorYPujaGanadora(prod, 12.5, pujaGanadoraActual);

        // compradorD iguala la puja más alta:
        pujaD = pujaService.insertarPuja(14D, producto.getId(), compradorD.getId());
        puja = pujaDao.findById(pujaD.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.puja", pujaD.getId()));
        compararPujas(puja, pujaD);
        prod = productoDao.findById(producto.getId()).orElseThrow(() -> new InstanceNotFoundException("test.project.entities.producto", producto.getId()));
        compararProductoValorYPujaGanadora(prod, 14D, pujaGanadoraActual);
    }

    private void compararPujas(Puja puja1, Puja puja2) {
        assertEquals(puja1.getId(), puja2.getId());
        assertEquals(puja1.getUser(), puja2.getUser());
        assertEquals(puja1.getProducto(), puja2.getProducto());
        assertEquals(puja1.getValor(), puja2.getValor());
        assertEquals(puja1.getFecha(), puja2.getFecha());
    }

    private void compararProductoValorYPujaGanadora(Producto producto, Double valor, Puja puja) {
        assertEquals(producto.getValorActual(), valor);
        assertEquals(producto.getPujaGanadora().getId(), puja.getId());
    }

    @Test
    public void testPujaFallidaPujaSinValor() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        User user1 = signUpUser("PujadorMaster");

        // La restricción `NOT NULL` de la BD se aplica después de intentar guardar
        assertThrows(DataIntegrityViolationException.class,
                () -> pujaService.insertarPuja(null, producto.getId(), user1.getId()));
    }

    @Test
    public void testPujaFallidaPujaSinUsuario() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        assertThrows(InvalidDataAccessApiUsageException.class, () -> pujaService.insertarPuja(7650D, producto.getId(), null));
        assertThrows(InstanceNotFoundException.class, () -> pujaService.insertarPuja(7650D, producto.getId(), 200L));
    }

    @Test
    public void testPujaFallidaPujaSinProducto() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        User user1 = signUpUser("PujadorMaster");
        Categoria categoria = addCategoria("Vehículos");

        assertThrows(InvalidDataAccessApiUsageException.class, () -> pujaService.insertarPuja(7650D, null, user1.getId()));
        assertThrows(InstanceNotFoundException.class, () -> pujaService.insertarPuja(7650D, 200L, user1.getId()));

        Producto producto = addProducto(categoria.getId(), user.getId());

        assertThrows(InvalidDataAccessApiUsageException.class, () -> pujaService.insertarPuja(7650D, null, user1.getId()));
        assertThrows(InstanceNotFoundException.class, () -> pujaService.insertarPuja(7650D, 200L, user1.getId()));
    }

    @Test
    public void testPujaFallidaPujaFueraDePlazo() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());
        producto.setFechaFinPuja(LocalDateTime.now().withNano(0).minusDays(10));
        productoDao.save(producto);
        User user1 = signUpUser("PujadorMaster");

        // La restricción `NOT NULL` de la BD se aplica después de intentar guardar
        assertThrows(PujaFueraDePlazoException.class,
                () -> pujaService.insertarPuja(300D, producto.getId(), user1.getId()));
    }

    @Test
    public void testPujaFallidaPujadorEsPublicador() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        // La restricción `NOT NULL` de la BD se aplica después de intentar guardar
        assertThrows(PujaUsuarioPublicadorException.class,
                () -> pujaService.insertarPuja(300D, producto.getId(), user.getId()));
    }

    @Test
    public void testPujaFallidaValorPujaInsuficiente() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());
        User user1 = signUpUser("PujadorMaster");

        // La restricción `NOT NULL` de la BD se aplica después de intentar guardar
        assertThrows(PujaValorNoValidoException.class,
                () -> pujaService.insertarPuja(9.99, producto.getId(), user1.getId()));
        assertThrows(PujaValorNoValidoException.class,
                () -> pujaService.insertarPuja(9D, producto.getId(), user1.getId()));
    }

    @Test
    public void testGetPujasByUser_UsuarioConPujas() throws InstanceNotFoundException, PujaUsuarioPublicadorException, PujaFueraDePlazoException, PujaValorNoValidoException {
        User user = signUpUser("vendedor");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        User compradorA = signUpUser("compradorA");
        User compradorB = signUpUser("compradorB");

        // Insertar pujas
        Puja pujaA = pujaService.insertarPuja(12D, producto.getId(), compradorA.getId());
        Puja pujaB = pujaService.insertarPuja(15D, producto.getId(), compradorB.getId());

        int start = 0;
        int size = 2;

        Block<Puja> result = pujaService.getPujasByUser(compradorA.getId(), start, size);

        assertNotNull(result);
        assertEquals(1, result.getItems().size()); // compradorA solo tiene una puja
        assertFalse(result.getExistMoreItems()); // No hay más pujas después de esta página

        // Verificar que la puja es la correcta
        Puja pujaEnPagina = result.getItems().get(0);
        assertEquals(pujaA.getId(), pujaEnPagina.getId());
        assertEquals(12D, pujaEnPagina.getValor());
    }

    @Test
    public void testGetPujasByUser_UsuarioSinPujas() throws InstanceNotFoundException {
        // Arrange
        User user = signUpUser("vendedor");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        User compradorA = signUpUser("compradorA");

        int start = 0;
        int size = 10;

        // Act
        Block<Puja> result = pujaService.getPujasByUser(compradorA.getId(), start, size);

        // Assert
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty()); // No hay pujas para este usuario
        assertFalse(result.getExistMoreItems()); // No hay más pujas
    }

    @Test
    public void testGetPujasByUser_Paginacion() throws InstanceNotFoundException, PujaUsuarioPublicadorException, PujaFueraDePlazoException, PujaValorNoValidoException {
        // Arrange
        User user = signUpUser("vendedor");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        User compradorA = signUpUser("compradorA");

        // Insertar múltiples pujas
        pujaService.insertarPuja(12D, producto.getId(), compradorA.getId());
        pujaService.insertarPuja(15D, producto.getId(), compradorA.getId());
        pujaService.insertarPuja(20D, producto.getId(), compradorA.getId());

        int start = 1;
        int size = 2;

        // Act
        Block<Puja> result = pujaService.getPujasByUser(compradorA.getId(), start, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().size()); // Se esperan 2 pujas en la segunda página
        assertFalse(result.getExistMoreItems()); // No hay más pujas después de esta página
    }

    @Test
    public void testGetPujasByUser_UsuarioNoEncontrado() {
        // Arrange
        Long userId = 999L; // Usuario que no existe
        int start = 0;
        int size = 10;

        assertThrows(InstanceNotFoundException.class, () -> {
            pujaService.getPujasByUser(userId, start, size);
        });

    }

    @Test
    public void testGetPujasByUser_PaginacionLimitePagina() throws InstanceNotFoundException, PujaUsuarioPublicadorException, PujaFueraDePlazoException, PujaValorNoValidoException {
        // Arrange
        User user = signUpUser("vendedor");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        User compradorA = signUpUser("compradorA");

        // Insertar exactamente 2 pujas
        pujaService.insertarPuja(12D, producto.getId(), compradorA.getId());
        pujaService.insertarPuja(15D, producto.getId(), compradorA.getId());

        int start = 0;
        int size = 2;

        // Act
        Block<Puja> result = pujaService.getPujasByUser(compradorA.getId(), start, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size()); // Debe devolver exactamente 2 pujas
        assertFalse(result.getExistMoreItems()); // No debe haber más pujas después de esta página
    }

    @Test
    public void testGetPujasByUser_PaginacionConMasPujas() throws InstanceNotFoundException, PujaUsuarioPublicadorException, PujaFueraDePlazoException, PujaValorNoValidoException {
        // Arrange
        User user = signUpUser("vendedor");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = addProducto(categoria.getId(), user.getId());

        User compradorA = signUpUser("compradorA");

        // Insertar 3 pujas
        pujaService.insertarPuja(12D, producto.getId(), compradorA.getId());
        pujaService.insertarPuja(15D, producto.getId(), compradorA.getId());
        pujaService.insertarPuja(20D, producto.getId(), compradorA.getId());

        int start = 0;
        int size = 2;

        // Act
        Block<Puja> result = pujaService.getPujasByUser(compradorA.getId(), start, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getItems().size()); // Debe devolver 2 pujas en la primera página
        assertTrue(result.getExistMoreItems()); // Debe haber más pujas después de esta página
    }

}
