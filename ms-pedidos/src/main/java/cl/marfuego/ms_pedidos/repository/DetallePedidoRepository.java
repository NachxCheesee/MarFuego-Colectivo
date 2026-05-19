package cl.marfuego.ms_pedidos.repository;

import cl.marfuego.ms_pedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}
