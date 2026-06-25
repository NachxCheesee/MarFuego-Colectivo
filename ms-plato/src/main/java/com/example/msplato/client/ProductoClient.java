package com.example.msplato.client;

import com.example.msplato.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-productos", url = "${MS_PRODUCTO_URI:http://localhost:8082}")
public interface ProductoClient {
    @GetMapping("/api/productos/{id}")
    ProductoDto obtenerProductosPorId(@PathVariable("id") Long id);
}
