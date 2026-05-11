package com.example.producto.client;

import com.example.producto.dto.LocalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-local", url= "http://localhost:8081")
public interface LocalClient {
    @GetMapping("/api/locales/{id}")
    LocalDto obtenerLocalPorId(@PathVariable("id") Long id);
}
