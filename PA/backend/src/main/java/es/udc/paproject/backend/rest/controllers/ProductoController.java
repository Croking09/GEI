package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ProductoService;
import es.udc.paproject.backend.rest.dtos.AddProductParamsDto;
import es.udc.paproject.backend.rest.dtos.BlockDto;
import es.udc.paproject.backend.rest.dtos.ResumenProductoDto;
import es.udc.paproject.backend.rest.dtos.VerProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static es.udc.paproject.backend.rest.dtos.ProductoConversor.toResumenProductoDtos;
import static es.udc.paproject.backend.rest.dtos.verProductosConversor.toVerProductoDtos;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public Long insertarProducto(@RequestAttribute Long userId,
                                 @Validated @RequestBody AddProductParamsDto params) throws InstanceNotFoundException {

        return productoService.insertarProducto(params.getNombre(), params.getDescripcion(), params.getDuracionPuja(),
                params.getPrecioSalida(), params.getInformacionEnvio(), params.getCategoriaId(), userId).getId();
    }

    @GetMapping("/user")
    public BlockDto<VerProductoDto> verProductosPorUsuario(@RequestAttribute Long userId,
                                                           @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException {

        Block<Producto> productoBlock = productoService.verProductosPorUsuario(userId, page, 2);

        return new BlockDto<>(toVerProductoDtos(productoBlock.getItems()), productoBlock.getExistMoreItems());
    }
}
