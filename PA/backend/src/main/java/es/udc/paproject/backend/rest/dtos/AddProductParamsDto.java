package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddProductParamsDto {

    private String nombre;
    private String descripcion;
    private int duracionPuja;
    private double precioSalida;
    private String informacionEnvio;
    private Long categoriaId;

    @NotNull
    @Size(min=1, max=60)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NotNull
    @Size(min=1, max=2000)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NotNull
    @Min(0)
    public int getDuracionPuja() {
        return duracionPuja;
    }

    public void setDuracionPuja(int duracionPuja) {
        this.duracionPuja = duracionPuja;
    }

    @NotNull
    @Min(0)
    public double getPrecioSalida() {
        return precioSalida;
    }

    public void setPrecioSalida(double precioSalida) {
        this.precioSalida = precioSalida;
    }

    @NotNull
    @Size(min=1, max=2000)
    public String getInformacionEnvio() {
        return informacionEnvio;
    }

    public void setInformacionEnvio(String informacionEnvio) {
        this.informacionEnvio = informacionEnvio;
    }

    @NotNull
    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
