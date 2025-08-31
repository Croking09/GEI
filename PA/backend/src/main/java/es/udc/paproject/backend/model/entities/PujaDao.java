package es.udc.paproject.backend.model.entities;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PujaDao extends JpaRepository<Puja, Long> {

    // FUNC-5
    Page<Puja> findByUserOrderByFechaDesc(User user, Pageable pageable);
    //Busca las pujas realizadas por un usuario, ordenadas de la más reciente a la más antigua.

}

