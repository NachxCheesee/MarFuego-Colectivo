package cl.marfuego.ms_inventario.client;

import cl.marfuego.ms_inventario.DTO.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-producto", url = "http://localhost:8082")
public interface ProductoClient {
    @GetMapping("/api/productos/{id}")
    ProductoDTO obtenerProductosPorId(@PathVariable("id") Long id);
}
