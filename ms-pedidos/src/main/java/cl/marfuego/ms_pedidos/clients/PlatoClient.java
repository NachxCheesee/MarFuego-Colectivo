package cl.marfuego.ms_pedidos.clients;

import cl.marfuego.ms_pedidos.dto.PlatoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-plato", url = "${MS_PLATO_URI:http://localhost:8086}")
public interface PlatoClient {
    @GetMapping("/plato/{id}")
    PlatoDTO obtenerPlatoPorId(@PathVariable("id")Long id);
}
