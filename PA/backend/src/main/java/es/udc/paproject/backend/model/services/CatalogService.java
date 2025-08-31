package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Categoria;
import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface CatalogService {

    List<Categoria> findAllCategorias();

    Block<Producto> findProductos(Long categoriaId, String keywords, int page, int size);

    // FUNC-3
    Producto verDetallesProducto(Long productoId) throws InstanceNotFoundException;
}
