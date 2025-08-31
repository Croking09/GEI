package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.EstadosPuja;

import java.time.LocalDateTime;

public class VerPujaDto {

    private LocalDateTime fecha;
    private String nombreProducto;
    private String enlace;
    private Double maxPujado;
    private EstadosPuja estado;

    public VerPujaDto() {}

    public VerPujaDto(String nombreProducto, EstadosPuja estado, LocalDateTime fecha, Double maxPujado, String enlace) {
        this.estado = estado;
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
        this.maxPujado = maxPujado;
        this.enlace = enlace;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public EstadosPuja getEstado() {
        return estado;
    }

    public void setEstado(EstadosPuja estado) {
        this.estado = estado;
    }

    public Double getMaxPujado() {
        return maxPujado;
    }

    public void setMaxPujado(Double maxPujado) {
        this.maxPujado = maxPujado;
    }
}
