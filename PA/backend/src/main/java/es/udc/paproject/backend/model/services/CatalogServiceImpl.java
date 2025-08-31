package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Categoria;
import es.udc.paproject.backend.model.entities.CategoriaDao;
import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.entities.ProductoDao;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private ProductoDao productoDao;

    @Override
    public List<Categoria> findAllCategorias() {
        return categoriaDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Block<Producto> findProductos(Long categoriaId, String keywords, int page, int size) {

        Slice<Producto> slice = productoDao.find(categoriaId, keywords, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());

    }

    @Override
    public Producto verDetallesProducto(Long productoId) throws InstanceNotFoundException {
        Optional<Producto> producto = productoDao.findById(productoId);

        if(producto.isEmpty()) {
            throw new InstanceNotFoundException("project.entities.producto", productoId);
        }

        return producto.get();
    }
}
