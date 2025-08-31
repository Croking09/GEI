package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Categoria;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaConversor {

    private CategoriaConversor() {}

    public final static CategoriaDto toCategoriaDto(Categoria categoria) {
        return new CategoriaDto(categoria.getId(), categoria.getName());
    }

    public final static List<CategoriaDto> toCategoriaDtos(List<Categoria> categorias) {
        return categorias.stream().map(c -> toCategoriaDto(c)).collect(Collectors.toList());
    }
}
