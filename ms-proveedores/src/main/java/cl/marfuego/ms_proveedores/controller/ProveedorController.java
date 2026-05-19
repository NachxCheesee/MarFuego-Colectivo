package cl.marfuego.ms_proveedores.controller;


import cl.marfuego.ms_proveedores.dto.ProveedorDto;
import cl.marfuego.ms_proveedores.model.Proveedor;
import cl.marfuego.ms_proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable Long id) {
        // Si no existe, el Service lanza ErrorNoEncontrado y el Capturador responde 404
        return ResponseEntity.ok(proveedorService.buscarProveedorPorId(id));
    }

    @PostMapping
    public ResponseEntity<Proveedor> guardarProveedor(@Valid @RequestBody ProveedorDto dto) {
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        Proveedor nuevoProveedor = proveedorService.guardarProveedor(dto);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }



}
