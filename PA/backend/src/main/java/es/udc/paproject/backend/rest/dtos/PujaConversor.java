package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.EstadosPuja;
import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.entities.Puja;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PujaConversor {

    private PujaConversor() {}

    public static VerPujaDto toVerPujaDto(Puja puja) {
        Producto producto = puja.getProducto();

        EstadosPuja estado;

        if (Objects.equals(producto.getPujaGanadora(), puja)) {
            estado = producto.getFechaFinPuja().isBefore(LocalDateTime.now()) ? EstadosPuja.GANADORA : EstadosPuja.GANANDO;
        } else {
            estado = EstadosPuja.PERDEDORA;
        }

        String enlaceProducto = "/catalog/productos/" + producto.getId();

        return new VerPujaDto(
                producto.getNombre(),
                estado,
                puja.getFecha(),
                puja.getValor(),
                enlaceProducto
        );
    }

    public static List<VerPujaDto> toVerPujaDtos(List<Puja> pujas) {
        return pujas.stream().map(PujaConversor::toVerPujaDto).collect(Collectors.toList());
    }
}