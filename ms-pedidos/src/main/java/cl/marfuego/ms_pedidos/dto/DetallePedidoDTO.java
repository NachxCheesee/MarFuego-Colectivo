package cl.marfuego.ms_pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(title = "DetallePedidoDTO", description = "Objeto de transferencia de datos que representa un plato específico dentro de un pedido")
public class DetallePedidoDTO {

    @NotNull(message = "el id del plato es obligatorio")
    @Schema(description = "Id del plato que se está solicitando", example = "4")
    private Long plato_id;

    @NotNull(message = "la cantidad de platos es obligatoria")
    @Min(value = 1, message = "La cantidad minima es 1")
    @Schema(description = "Cantidad de porciones del plato solicitadas", example = "3")
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
