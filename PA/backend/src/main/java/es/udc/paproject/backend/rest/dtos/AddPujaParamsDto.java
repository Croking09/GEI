package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;

public class AddPujaParamsDto {

    // Atributos --------------------------------------------------------------

    Double valor;
    Long productoId;

    // Constructores ----------------------------------------------------------

    public  AddPujaParamsDto() {}

    public AddPujaParamsDto(Double valor, Long productoId) {
        this.valor = valor;
        this.productoId = productoId;
    }

    // Métodos ----------------------------------------------------------------

    @NotNull
    @Min(0)
    public Double getValor() {
        return valor;
    }


    public void setValor(Double valor) {
        this.valor = valor;
    }

    @NotNull
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

}
