package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ProductoService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductoServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoDao productoDao;

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

    @Test
    public void testInsertarProductoCorrecto() throws InstanceNotFoundException {
        User user = signUpUser("Juan");
        Categoria categoria = addCategoria("Cuadros");

        Producto producto = productoService.insertarProducto("Las meninas",
                "Cuadro de Velazquez", 30, 1000, "Se manda por Correos",
                categoria.getId(), user.getId());
        LocalDateTime fechaPublicacionTest = LocalDateTime.now().withNano(0);
        LocalDateTime fechaFinPujaTest = fechaPublicacionTest.plusMinutes(30);

        assertEquals("Las meninas", producto.getNombre());
        assertEquals("Cuadro de Velazquez", producto.getDescripcion());
        assertEquals(producto.getFechaPublicacion(), fechaPublicacionTest);
        assertEquals(producto.getFechaFinPuja(), fechaFinPujaTest);
        assertEquals(1000, producto.getPrecioSalida());
        assertEquals(1000, producto.getValorActual());
        assertEquals("Se manda por Correos", producto.getInformacionEnvio());
        assertEquals("Cuadros", producto.getCategoria().getName());
        assertEquals("Juan", producto.getPublicador().getUserName());

    }

    @Test
    public void testInsertarProductoSinCategoria() throws InstanceNotFoundException {
        User user = signUpUser("Juan");
        Categoria categoria = addCategoria("Estatuas");

        assertThrows(InstanceNotFoundException.class, () ->
                productoService.insertarProducto("Las meninas", "Cuadro de Velazquez", 30,
                        1000, "Se manda por Correos", categoria.getId()+1, user.getId()));
    }

    @Test
    public void testInsertarProductoSinUsuario() throws InstanceNotFoundException {
        User user = signUpUser("Juan");
        Categoria categoria = addCategoria("Cuadros");

        assertThrows(InstanceNotFoundException.class, () ->
                productoService.insertarProducto("Las meninas", "Cuadro de Velazquez", 30,
                        1000, "Se manda por Correos", categoria.getId(), user.getId()+1));
    }

    @Test
    public void testVerProductosPorUsuario() throws InstanceNotFoundException {
        User user = signUpUser("Vehículos");
        Categoria categoria = addCategoria("Raptorsito");

        Producto producto1 = productoService.insertarProducto(
                "Toyota Hilux",
                "Automóvil",
                30,
                10000,
                "Se entrega en concesionario local",
                categoria.getId(),
                user.getId()
        );
        Producto producto2 = productoService.insertarProducto(
                "Toyota Corolla",
                "Automóvil",
                40,
                15000,
                "Se entrega en concesionario local",
                categoria.getId(),
                user.getId()
        );

        int page = 0; // Primera página
        int size = 1; // Tamaño de página de 1
        Block<Producto> result = productoService.verProductosPorUsuario(user.getId(), page, size);

        assertNotNull(result);
        assertEquals(1, result.getItems().size()); // Usar getItems() en lugar de getContent()
        assertTrue(result.getExistMoreItems()); // Usar getExistMoreItems() en lugar de hasNext()

        // Verificar que el producto en la página es el correcto (el más reciente)
        Producto productoEnPagina = result.getItems().getFirst();
        assertEquals(producto2.getId(), productoEnPagina.getId()); // El segundo producto es más reciente
        assertEquals("Toyota Corolla", productoEnPagina.getNombre());

        // Act: Obtener la segunda página
        page = 1;
        result = productoService.verProductosPorUsuario(user.getId(), page, size);

        assertNotNull(result);
        assertEquals(1, result.getItems().size()); // Usar getItems() en lugar de getContent()
        assertFalse(result.getExistMoreItems()); // Usar getExistMoreItems() en lugar de hasNext()

        // Verificar que el producto en la segunda página es el correcto
        productoEnPagina = result.getItems().getFirst();
        assertEquals(producto1.getId(), productoEnPagina.getId()); // El primer producto es el más antiguo
        assertEquals("Toyota Hilux", productoEnPagina.getNombre());
    }

    @Test
    public void testVerProductosPorUsuario_UsuarioNoExiste() {

        Long userId = 999L; // Usuario que no existe
        int start = 0;
        int size = 10;

        assertThrows(InstanceNotFoundException.class, () -> {
            productoService.verProductosPorUsuario(userId, start, size);
        });
    }

}
