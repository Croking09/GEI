package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.EstadosPuja;
import jakarta.validation.constraints.NotNull;

public class PujaRealizadaDto {

    // Atributos --------------------------------------------------------------

    Long pujaId;
    EstadosPuja estadoPuja;
    Long tiempoRestante;
    Double precioActual;

    // Constructores ----------------------------------------------------------

    public PujaRealizadaDto() {}

    public PujaRealizadaDto(Long pujaId, EstadosPuja estadoPuja, Long tiempoRestante, Double precioActual) {
        this.pujaId = pujaId;
        this.estadoPuja = estadoPuja;
        this.tiempoRestante = tiempoRestante;
        this.precioActual = precioActual;
    }

    // Métodos ----------------------------------------------------------------

    @NotNull
    public Long getPujaId() {
        return pujaId;
    }

    public void setPujaId(Long pujaId) {
        this.pujaId = pujaId;
    }

    @NotNull
    public EstadosPuja getEstadoPuja() {
        return estadoPuja;
    }

    public void setEstadoPuja(EstadosPuja estadoPuja) {
        this.estadoPuja = estadoPuja;
    }

    @NotNull
    public Long getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(Long tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    @NotNull
    public Double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(Double precioActual) {
        this.precioActual = precioActual;
    }

}
