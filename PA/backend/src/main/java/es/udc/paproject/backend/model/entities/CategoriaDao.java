package es.udc.paproject.backend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Optional;

public interface CategoriaDao extends CrudRepository<Categoria, Long>, ListPagingAndSortingRepository<Categoria, Long> {}