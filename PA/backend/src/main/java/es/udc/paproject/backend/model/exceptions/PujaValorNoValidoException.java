package es.udc.paproject.backend.model.exceptions;

public class PujaValorNoValidoException extends InstanceException {

    private final Double valorPuja;
    private final Long productoId;
    private final Double valorActualProducto;

    public PujaValorNoValidoException(Double valorPuja, Long productoId, Double valorActualProducto) {
        super(valorPuja + " " + productoId + " " + valorActualProducto);
        this.valorPuja = valorPuja;
        this.productoId = productoId;
        this.valorActualProducto = valorActualProducto;
    }

    public Double getValorPuja() {
        return valorPuja;
    }

    public Long getProductoId() {
        return productoId;
    }

    public Double getValorActualProducto() {
        return valorActualProducto;
    }

}
