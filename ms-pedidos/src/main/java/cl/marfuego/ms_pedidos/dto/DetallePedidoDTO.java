package cl.marfuego.ms_pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DetallePedidoDTO {
    @NotNull(message = "el id del plato es obligatorio")
    private Long plato_id;
    @NotNull(message = "la cantidad de platos es obligatoria")
    @Min(value = 1, message = "La cantidad minima es 1")
    private Integer cantidad;

    public DetallePedidoDTO() {
    }

    public DetallePedidoDTO(Long plato_id, Integer cantidad) {
        this.plato_id = plato_id;
        this.cantidad = cantidad;
    }

    public Long getPlato_id() {
        return plato_id;
    }

    public void setPlato_id(Long plato_id) {
        this.plato_id = plato_id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
