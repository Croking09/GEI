package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DetallesProductoDto {

    // Atributos --------------------------------------------------------------

    private Long id;
    private String nombre;
    private String descripcion;
    private Long idCategoria;
    private String publicador;
    private LocalDateTime fechaPublicacion;
    private Long tiempoRestante;
    private Double precioSalida;
    private Double valorActual;
    private String informacionEnvio;
    private Boolean tienePujas;

    // Constructores ----------------------------------------------------------

    public DetallesProductoDto() {}

    public DetallesProductoDto(Long id,
                               String nombre,
                               String descripcion,
                               Long idCategoria,
                               String publicador,
                               LocalDateTime fechaPublicacion,
                               Long tiempoRestante,
                               Double precioSalida,
                               Double valorActual,
                               String informacionEnvio,
                               Boolean tienePujas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.publicador = publicador;
        this.fechaPublicacion = fechaPublicacion;
        this.tiempoRestante = tiempoRestante;
        this.precioSalida = precioSalida;
        this.valorActual = valorActual;
        this.informacionEnvio = informacionEnvio;
        this.tienePujas = tienePujas;
    }

    // Métodos ----------------------------------------------------------------

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NotNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NotNull
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    @NotNull
    public String getPublicador() {
        return publicador;
    }

    public void setPublicador(String publicador) {
        this.publicador = publicador;
    }

    @NotNull
    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @NotNull
    public Long getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(Long tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    @NotNull
    public Double getPrecioSalida() {
        return precioSalida;
    }

    public void setPrecioSalida(Double precioSalida) {
        this.precioSalida = precioSalida;
    }

    @NotNull
    public Double getValorActual() {
        return valorActual;
    }

    public void setValorActual(Double valorActual) {
        this.valorActual = valorActual;
    }

    @NotNull
    public String getInformacionEnvio() {
        return informacionEnvio;
    }

    public void setInformacionEnvio(String informacionEnvio) {
        this.informacionEnvio = informacionEnvio;
    }

    @NotNull
    public Boolean getTienePujas() {
        return tienePujas;
    }

    public void setTienePujas(Boolean tienePujas) {
        this.tienePujas = tienePujas;
    }
}
