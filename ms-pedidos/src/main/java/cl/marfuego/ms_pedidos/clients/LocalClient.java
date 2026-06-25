package cl.marfuego.ms_pedidos.clients;

import cl.marfuego.ms_pedidos.dto.LocalDTO;
import cl.marfuego.ms_pedidos.dto.MesaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-locales", url = "${MS_LOCALES_URI:http://localhost:8081}")
public interface LocalClient {
    @GetMapping("/locales/{id}")
    LocalDTO obtenerLocalPorId(@PathVariable("id")Long id);
    @GetMapping("/mesas/{id}")
    MesaDTO obtenerMesaPorId(@PathVariable("id")Long id);
}
