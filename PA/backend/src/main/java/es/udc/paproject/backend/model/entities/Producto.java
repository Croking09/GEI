package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Producto {

    // Atributos --------------------------------------------------------------

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaFinPuja;
    private Double precioSalida;
    private Double valorActual;
    private String informacionEnvio;
    private Categoria categoria;
    private User publicador;
    private Long version;
    private Puja pujaGanadora;



    // Constructores ----------------------------------------------------------

    public Producto() {}

    public Producto(Long id, String nombre, String descripcion, LocalDateTime fechaPublicacion, LocalDateTime finPuja, Double precioSalida, Double valorActual, String informacionEnvio, Categoria categoria, User publicador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaFinPuja = finPuja;
        this.precioSalida = precioSalida;
        this.valorActual = valorActual;
        this.informacionEnvio = informacionEnvio;
        this.categoria = categoria;
        this.publicador = publicador;
        this.pujaGanadora = null;
    }

    public Producto(String nombre, String descripcion, LocalDateTime fechaPublicacion, LocalDateTime finPuja, Double precioSalida, Double valorActual, String informacionEnvio, Categoria categoria, User publicador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaFinPuja = finPuja;
        this.precioSalida = precioSalida;
        this.valorActual = valorActual;
        this.informacionEnvio = informacionEnvio;
        this.categoria = categoria;
        this.publicador = publicador;
        this.pujaGanadora = null;
    }

    public Producto(Long id, String nombre, String descripcion, LocalDateTime fechaPublicacion, LocalDateTime fechaFinPuja, Double precioSalida, Double valorActual, String informacionEnvio, Categoria categoria, User publicador, Long version, Puja pujaGanadora) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaFinPuja = fechaFinPuja;
        this.precioSalida = precioSalida;
        this.valorActual = valorActual;
        this.informacionEnvio = informacionEnvio;
        this.categoria = categoria;
        this.publicador = publicador;
        this.version = version;
        this.pujaGanadora = pujaGanadora;
    }

    // Métodos ----------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDateTime getFechaFinPuja() {
        return fechaFinPuja;
    }

    public void setFechaFinPuja(LocalDateTime fechaFinPuja) {
        this.fechaFinPuja = fechaFinPuja;
    }

    public Double getPrecioSalida() {
        return precioSalida;
    }

    public void setPrecioSalida(Double precioSalida) {
        this.precioSalida = precioSalida;
    }

    public Double getValorActual() {
        return valorActual;
    }

    public void setValorActual(Double valorActual) {
        this.valorActual = valorActual;
    }

    public String getInformacionEnvio() {
        return informacionEnvio;
    }

    public void setInformacionEnvio(String informacionEnvio) {
        this.informacionEnvio = informacionEnvio;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="categoriaId")
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    public User getPublicador() {
        return publicador;
    }

    public void setPublicador(User publicador) {
        this.publicador = publicador;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pujaGanadoraId", unique = true)
    public Puja getPujaGanadora() {
        return pujaGanadora;
    }

    public void setPujaGanadora(Puja pujaGanadora) {
        this.pujaGanadora = pujaGanadora;
    }

    public Long obtenerTiempoRestante() {
        if (fechaFinPuja == null || fechaPublicacion == null)
            return 0L; // Retorna null si alguna de las fechas no está definida
        return java.time.Duration.between(LocalDateTime.now().withNano(0), fechaFinPuja).toMinutes();
    }

    public Boolean tienePujas() {
        return pujaGanadora != null;
    }

}
