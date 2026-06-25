package cl.marfuego.ms_pedidos.clients;

import cl.marfuego.ms_pedidos.dto.EmpleadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empleados", url = "${MS_EMPLEADOS_URI:http://localhost:8083}")
public interface EmpleadoClient {
    @GetMapping("/empleados/{id}")
    EmpleadoDTO obtenerEmpleadoPorId(@PathVariable("id")Long id);
}
