package com.example.msplato.controller;

import com.example.msplato.dto.PlatoDto;
import com.example.msplato.model.Plato;
import com.example.msplato.service.PlatoService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {
    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }
    @GetMapping
    public ResponseEntity<List<Plato>> listarPlatos(){
        return ResponseEntity.ok(platoService.listarPlatos());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Plato> obtenerPlatoPorId(@PathVariable Long id){
        return ResponseEntity.ok(platoService.buscarPlatoPorId(id));

    }
    @PostMapping
    public ResponseEntity<Plato> guardarPlato(@Valid @RequestBody PlatoDto dto) throws BadRequestException {
        Plato platoNuevo = platoService.guardarPlato(dto);
        return new ResponseEntity<>(platoNuevo, HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Plato> actualizarPlato(@PathVariable Long id, @Valid @RequestBody PlatoDto dto) throws BadRequestException {
        Plato platoActualizado = platoService.actualizarPlato(id, dto);
        return ResponseEntity.ok(platoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Long id){
        platoService.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }

}
