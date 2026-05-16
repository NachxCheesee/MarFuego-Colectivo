package com.example.msplato.client;

import com.example.msplato.dto.LocalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-locales", url = "http://localhost:8081")
public interface LocalClient {

    @GetMapping("/api/locales/{id}")
    LocalDto obtenerLocalPorId(@PathVariable("id") Long id);
}