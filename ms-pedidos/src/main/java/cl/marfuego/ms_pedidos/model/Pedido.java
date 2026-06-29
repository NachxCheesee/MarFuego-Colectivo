package cl.marfuego.ms_pedidos.model;

import cl.marfuego.ms_pedidos.enums.TipoPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Schema(description = "Entidad que representa un pedido en el sistema")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico autoincrementable del pedido",
            example = "1")
    private Long id;

    @Column(name = "tipo_pedido", nullable = false)
    @Schema(description = "Tipo de pedido: local o delivery",example = "delivery")
    private TipoPedido tipopedido;

    //datos por si el pedido es en el local
    @Column(name = "local_id")
    @Schema(description = "Id del local donde se realiza el pedido", example = "1")
    private Long local_id;

    @Column(name = "mesa_id")
    @Schema(description = "Id de la mesa asociada al pedido", example = "3")
    private Long mesa_id;

    //dato por si el pedido es por delivery
    @Column(name = "empleado_id")
    @Schema(description = "Id del repartidor", example = "5")
    private Long empleado_id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id")
    @Schema(description = "Lista de detalles que componen el pedido")
    private List<DetallePedido> detalles = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Long id, TipoPedido tipopedido, Long local_id, Long mesa_id, Long empleado_id, List<DetallePedido> detalles) {
        this.id = id;
        this.tipopedido = tipopedido;
        this.local_id = local_id;
        this.mesa_id = mesa_id;
        this.empleado_id = empleado_id;
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPedido getTipopedido() {
        return tipopedido;
    }

    public void setTipopedido(TipoPedido tipopedido) {
        this.tipopedido = tipopedido;
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

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}
