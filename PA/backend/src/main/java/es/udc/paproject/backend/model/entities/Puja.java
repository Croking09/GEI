package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Puja {

    // Atributos --------------------------------------------------------------

    Long id;             // Id de la puja
    User user;           // Usuario que hace la puja
    Producto producto;   // Producto por el que se puja
    Double valor;        // Valor de la puja
    LocalDateTime fecha; // Fecha y hora en la que se hace la puja

    // Constructores ----------------------------------------------------------

    public Puja () {}

    public Puja(Long id, User user, Producto producto, Double valor, LocalDateTime fecha) {
        this.id = id;
        this.user = user;
        this.producto = producto;
        this.valor = valor;
        this.fecha = fecha;
    }

    public Puja(User user, Producto producto, Double valor) {
        this.user = user;
        this.producto = producto;
        this.valor = valor;
        this.fecha = LocalDateTime.now().withNano(0);
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

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "productoId")
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadosPuja obtenerEstadoActual() {
        if (Objects.equals(this.id, producto.getPujaGanadora().getId())) {
            if (producto.getFechaFinPuja().isBefore(LocalDateTime.now()))
                return EstadosPuja.GANADORA;
            else return EstadosPuja.GANANDO;
        }
        else return EstadosPuja.PERDEDORA;
    }
}
