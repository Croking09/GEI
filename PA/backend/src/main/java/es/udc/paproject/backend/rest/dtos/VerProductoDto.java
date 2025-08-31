package es.udc.paproject.backend.rest.dtos;

public class VerProductoDto {

    private String nombre;
    private String enlace;
    private Double precioActual;
    private Integer minutosRestantesPuja;
    private Boolean finalizada;
    private String correoGanador;

    public VerProductoDto(){}

    public VerProductoDto(String nombre, String enlace, Double precioActual, Integer minutosRestantesPuja, String correoGanador) {
        this.nombre = nombre;
        this.enlace = enlace;
        this.precioActual = precioActual;
        this.minutosRestantesPuja = minutosRestantesPuja;
        this.correoGanador = correoGanador;
        this.finalizada = this.minutosRestantesPuja <= 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(Double precioActual) {
        this.precioActual = precioActual;
    }

    public Integer getMinutosRestantesPuja() {
        return minutosRestantesPuja;
    }

    public void setMinutosRestantesPuja(Integer minutosRestantesPuja) {
        this.minutosRestantesPuja = minutosRestantesPuja;
    }

    public Boolean isFinalizada() {
        return finalizada;
    }

    public String getCorreoGanador() {
        return correoGanador;
    }

    public void setCorreoGanador(String correoGanador) {
        this.correoGanador = correoGanador;
    }
}
