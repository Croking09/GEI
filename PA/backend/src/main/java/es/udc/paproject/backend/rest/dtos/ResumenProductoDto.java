package es.udc.paproject.backend.rest.dtos;

public class ResumenProductoDto {

    private Long id;
    private String nombre;
    private Long categoriaId;
    private int minutosRestantes;
    private Double precioActual;

    public ResumenProductoDto() {}

    public ResumenProductoDto(Long id, String nombre, Long categoriaId, int minutosRestantes, Double precioActual) {
        this.id = id;
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.minutosRestantes = minutosRestantes;
        this.precioActual = precioActual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMinutosRestantes() {
        return minutosRestantes;
    }

    public void setMinutosRestantes(int minutosRestantes) {
        this.minutosRestantes = minutosRestantes;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(Double precioActual) {
        this.precioActual = precioActual;
    }
}
