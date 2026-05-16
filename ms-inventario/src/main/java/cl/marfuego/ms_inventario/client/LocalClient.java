package cl.marfuego.ms_inventario.client;

import cl.marfuego.ms_inventario.DTO.LocalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-locales", url = "http://localhost:8081")
public interface LocalClient {
    @GetMapping("/api/locales/{id}")
    LocalDTO obtenerLocalPorId(@PathVariable("id") Long id);
}
