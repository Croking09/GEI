package es.udc.paproject.backend.model.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto, Long>, CustomizedProductDao {

    // buscar productos de un usuario y ordenar por fecha de fin en orden descendente

    //Consulta equivalente en SQL
    //Preguntarle al profe

    //SELECT * FROM producto
    //WHERE user_id = ?
    //ORDER BY fecha_finalizacion DESC;

    Slice<Producto> findByPublicadorOrderByFechaFinPujaDesc(User publicador, Pageable pageable);

}
