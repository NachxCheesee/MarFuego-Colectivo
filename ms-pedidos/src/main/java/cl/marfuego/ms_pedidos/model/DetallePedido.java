package cl.marfuego.ms_pedidos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "detalles_pedidos")
@Schema(description = "Detalle de un pedido, incluye plato y la cantidad asociada")
public class DetallePedido{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico autoincrementable del detalle del pedido", example = "1")
    private Long id;

    @Column(name = "plato_id" ,nullable = false)
    @Schema(description = "Identificador del plato solicitado", example = "2")
    private Long plato_id;

    @Column(nullable = false)
    @Schema(description = "Cantidad de platos solicitados",example = "3")
    private Integer cantidad;

    public DetallePedido() {
    }

    public DetallePedido(Long id, Long plato_id, Integer cantidad) {
        this.id = id;
        this.plato_id = plato_id;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
