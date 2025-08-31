package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.Categoria;
import es.udc.paproject.backend.model.entities.CategoriaDao;
import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.ProductoService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UserService userService;

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
    public void testFindAllCategories() {

        Categoria category1 = new Categoria("category1");
        Categoria category2 = new Categoria("category2");

        categoriaDao.save(category2);
        categoriaDao.save(category1);

        assertEquals(Arrays.asList(category1, category2), catalogService.findAllCategorias());

    }

    @Test
    public void testFindProductsByKeywords() throws InstanceNotFoundException {

        User user = signUpUser("Juan");

        Categoria category = new Categoria("category");
        Categoria category2 = new Categoria("category2");

        categoriaDao.save(category);
        categoriaDao.save(category2);

        Producto product1 = productoService.insertarProducto("a Product", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product2 = productoService.insertarProducto("product B", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product3 = productoService.insertarProducto("product 1", "descripcion", -5,
                1000, "Correos", category.getId(), user.getId());
        Producto product4 = productoService.insertarProducto("another", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product5 = productoService.insertarProducto("b Product", "descripcion", 30,
                1000, "Correos", category2.getId(), user.getId());

        Block<Producto> expectedBlock1 = new Block<>(Arrays.asList(product1, product2), false);
        Block<Producto> actualBlock1 = catalogService.findProductos(category.getId(), "PrOd", 0 ,3);

        assertEquals(expectedBlock1, actualBlock1);

        Block<Producto> expectedBlock2 = new Block<>(Arrays.asList(product1, product5, product2), false);
        Block<Producto> actualBlock2 = catalogService.findProductos(null, "PrOd", 0 ,3);

        assertEquals(expectedBlock2, actualBlock2);

        Block<Producto> expectedBlock3 = new Block<>(Arrays.asList(product1, product4, product2), false);
        Block<Producto> actualBlock3 = catalogService.findProductos(category.getId(), null, 0 ,3);

        assertEquals(expectedBlock3, actualBlock3);

        Block<Producto> expectedBlock4 = new Block<>(Arrays.asList(product1, product4, product5, product2), false);
        Block<Producto> actualBlock4 = catalogService.findProductos(null, null, 0 ,4);

        assertEquals(expectedBlock4, actualBlock4);

    }

    @Test
    public void testFindNoProducts() throws InstanceNotFoundException {

        User user = signUpUser("Juan");

        Categoria category = new Categoria("category");
        categoriaDao.save(category);

        Producto product1 = productoService.insertarProducto("a Product", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product2 = productoService.insertarProducto("product B", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product3 = productoService.insertarProducto("product 1", "descripcion", -5,
                1000, "Correos", category.getId(), user.getId());
        Producto product4 = productoService.insertarProducto("another", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product5 = productoService.insertarProducto("b Product", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());

        Block<Producto> expectedBlock1 = new Block<>(new ArrayList<>(), false);
        Block<Producto> actualBlock1 = catalogService.findProductos(null, "nothing", 0 ,2);

        assertEquals(expectedBlock1, actualBlock1);

    }

    @Test
    public void testFindByPages() throws InstanceNotFoundException {
        User user = signUpUser("Juan");

        Categoria category = new Categoria("category");
        Categoria category2 = new Categoria("category2");
        categoriaDao.save(category);
        categoriaDao.save(category2);

        Producto product1 = productoService.insertarProducto("a Product", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product2 = productoService.insertarProducto("product B", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product3 = productoService.insertarProducto("product 1", "descripcion", -5,
                1000, "Correos", category.getId(), user.getId());
        Producto product4 = productoService.insertarProducto("another", "descripcion", 30,
                1000, "Correos", category.getId(), user.getId());
        Producto product5 = productoService.insertarProducto("b Product", "descripcion", 30,
                1000, "Correos", category2.getId(), user.getId());

        Block<Producto> expectedBlock1 = new Block<>(Arrays.asList(product1, product5), true);
        Block<Producto> actualBlock1 = catalogService.findProductos(null, "PrOd", 0 ,2);

        assertEquals(expectedBlock1, actualBlock1);

        Block<Producto> expectedBlock2 = new Block<>(Arrays.asList(product2), false);
        Block<Producto> actualBlock2 = catalogService.findProductos(null, "PrOd", 1 ,2);

        assertEquals(expectedBlock2, actualBlock2);

        Block<Producto> expectedBlock3 = new Block<>(new ArrayList<>(), false);
        Block<Producto> actualBlock3 = catalogService.findProductos(null, "PrOd", 2 ,2);

        assertEquals(expectedBlock3, actualBlock3);

    }

    @Test
    public void testVerDetallesProductoCorrecto() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = productoService.insertarProducto(
                "Toyota Hilux",
                "Automóvil",
                30,
                10000,
                "Se entrega en concesionario local",
                categoria.getId(),
                user.getId()
        );

        Producto p = catalogService.verDetallesProducto(producto.getId());
        assertEquals(p.getId(), producto.getId());
        assertEquals(p.getDescripcion(), producto.getDescripcion());
        assertEquals(p.getFechaPublicacion(), producto.getFechaPublicacion());
        assertEquals(p.getFechaFinPuja(), producto.getFechaFinPuja());
        assertEquals(p.getPrecioSalida(), producto.getPrecioSalida());
        assertEquals(p.getValorActual(), producto.getValorActual());
        assertEquals(p.getInformacionEnvio(), producto.getInformacionEnvio());
        assertEquals(p.getCategoria().getId(), producto.getCategoria().getId());
        assertEquals(p.getPublicador().getId(), producto.getPublicador().getId());
    }

    @Test
    public void testVerDetallesProductoFallo() throws InstanceNotFoundException {
        User user = signUpUser("Raptorsito");
        Categoria categoria = addCategoria("Vehículos");
        Producto producto = productoService.insertarProducto(
                "Toyota Hilux",
                "Automóvil",
                30,
                10000,
                "Se entrega en concesionario local",
                categoria.getId(),
                user.getId()
        );

        assertThrows(InstanceNotFoundException.class, () -> catalogService.verDetallesProducto(200L));
    }

}
