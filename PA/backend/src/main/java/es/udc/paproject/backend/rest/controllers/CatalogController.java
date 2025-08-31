package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.ProductoService;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.udc.paproject.backend.rest.dtos.CategoriaConversor.toCategoriaDtos;
import static es.udc.paproject.backend.rest.dtos.ProductoConversor.toResumenProductoDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/categories")
    public List<CategoriaDto> findAllCategorias() {
        return toCategoriaDtos(catalogService.findAllCategorias());
    }

    @GetMapping("/productos")
    public BlockDto<ResumenProductoDto> findAllProductos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String keywords,
            @RequestParam(defaultValue = "0") int page) {

        Block<Producto> productoBlock = catalogService.findProductos(categoriaId,
                keywords != null ? keywords.trim() : null, page, 2);

        return new BlockDto<>(toResumenProductoDtos(productoBlock.getItems()), productoBlock.getExistMoreItems());

    }

    @GetMapping("/productos/{id}")
    public DetallesProductoDto verDetallesProducto(
            @PathVariable Long id
    ) throws InstanceNotFoundException {
        return ProductoConversor.toDetallesProductoDto(catalogService.verDetallesProducto(id));
    }
}
