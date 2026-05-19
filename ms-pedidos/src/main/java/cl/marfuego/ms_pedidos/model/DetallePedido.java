package cl.marfuego.ms_pedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_pedidos")
public class DetallePedido{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plato_id" ,nullable = false)
    private Long plato_id;
    @Column(nullable = false)
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
