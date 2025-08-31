package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Slice;

public interface CustomizedProductDao {

    Slice<Producto> find(Long categoriaId, String keywords, int page, int size);

}
