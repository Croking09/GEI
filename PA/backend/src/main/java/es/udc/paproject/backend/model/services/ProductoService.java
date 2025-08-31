package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

public interface ProductoService {

    Producto insertarProducto(String nombre, String descripcion, int duracionPuja,
                              double precioSalida, String informacionEnvio, Long categoriaId,
                              Long publicadorId) throws InstanceNotFoundException;

    Block<Producto> verProductosPorUsuario(Long userId, int page, int size) throws InstanceNotFoundException;

    // FUNC-4: Función auxiliar
    Producto buscarProducto(Long id) throws InstanceNotFoundException;

}
