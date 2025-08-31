package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Producto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoConversor {

    private ProductoConversor() {}

    private final static ResumenProductoDto toResumenProductoDto(Producto producto) {
        return new ResumenProductoDto(producto.getId(), producto.getNombre(), producto.getCategoria().getId(),
                (int) ChronoUnit.MINUTES.between(LocalDateTime.now(), producto.getFechaFinPuja()), producto.getValorActual());
    }

    public final static List<ResumenProductoDto> toResumenProductoDtos(List<Producto> productos) {
        return productos.stream().map(p -> toResumenProductoDto(p)).collect(Collectors.toList());
    }

    public static DetallesProductoDto toDetallesProductoDto(Producto producto) {
        return new DetallesProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getCategoria().getId(),
                producto.getPublicador().getUserName(),
                producto.getFechaPublicacion(),
                producto.obtenerTiempoRestante(),
                producto.getPrecioSalida(),
                producto.getValorActual(),
                producto.getInformacionEnvio(),
                producto.tienePujas()
                );
    }

}
