package cl.marfuego.ms_pedidos.dto;

import cl.marfuego.ms_pedidos.enums.TipoPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(name = "PedidoDTO", description = "Objeto de transferencia de datos para la creación y la actualización de pedidos dependiendo del tipo de pedido")
public class PedidoDTO {

    @NotNull(message = "El tipo de pedido es obligatorio, favor de rellenar con (LOCAL o DELIVERY)")
    @Schema(description = "Tipo de pedido: local o delivery", example = "local")
    private TipoPedido tipoPedido;

    @Schema(description = "Id del local donde se realizará el pedido", example = "1")
    private Long local_id;

    @Schema(description = "Id de la mesa asignada al pedido", example = "3")
    private Long mesa_id;

    @Schema(description = "Id del empleado que gestiona el pedido", example = "3")
    private Long empleado_id;

    @NotEmpty(message = "el pedido debe contener almenos un plato, favor de rellenar")
    @Valid
    @Schema(description = "Lista de detalles del pedido, como platos y cantidades", required = true)
    private List<DetallePedidoDTO> detalles;

    public PedidoDTO() {
    }

    public PedidoDTO(TipoPedido tipoPedido, Long local_id, Long mesa_id, Long empleado_id, List<DetallePedidoDTO> detalles) {
        this.tipoPedido = tipoPedido;
        this.local_id = local_id;
        this.mesa_id = mesa_id;
        this.empleado_id = empleado_id;
        this.detalles = detalles;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public Long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Long local_id) {
        this.local_id = local_id;
    }

    public Long getMesa_id() {
        return mesa_id;
    }

    public void setMesa_id(Long mesa_id) {
        this.mesa_id = mesa_id;
    }

    public Long getEmpleado_id() {
        return empleado_id;
    }

    public void setEmpleado_id(Long empleado_id) {
        this.empleado_id = empleado_id;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }

    public Long getLocalId() {
        return local_id;
    }

    public Long getMesaId() {
        return mesa_id;
    }

    public TipoPedido getTipo() {
        return tipoPedido;
    }
}
