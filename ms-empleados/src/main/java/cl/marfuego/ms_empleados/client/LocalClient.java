package cl.marfuego.ms_empleados.client;


import cl.marfuego.ms_empleados.dto.LocalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-locales", url = "http://localhost:8081/api/locales")
public interface LocalClient {

    @GetMapping("/{id}")
    LocalDto obtenerDatosDeLocal(@PathVariable("id") Long id);

}
