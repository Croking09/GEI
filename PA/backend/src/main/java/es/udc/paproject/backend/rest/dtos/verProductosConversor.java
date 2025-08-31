package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Producto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class verProductosConversor {

    private verProductosConversor() {}

    private final static VerProductoDto toVerProductoDto(Producto producto) {

        String enlaceProducto="/catalog/productos/" + producto.getId();
        String correoGanador;

        if (producto.getPujaGanadora()!=null) {
            correoGanador=producto.getPujaGanadora().getUser().getEmail();
        }else{
            correoGanador = null;
        }

        return new VerProductoDto(producto.getNombre(), enlaceProducto, producto.getValorActual(),
                (int) ChronoUnit.MINUTES.between(LocalDateTime.now(), producto.getFechaFinPuja()), correoGanador);
    }

    public final static List<VerProductoDto> toVerProductoDtos(List<Producto> productos) {
        return productos.stream().map(verProductosConversor::toVerProductoDto).collect(Collectors.toList());
    }

}
