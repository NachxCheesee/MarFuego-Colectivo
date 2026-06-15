package cl.marfuego.ms_proveedores.controller;


import cl.marfuego.ms_proveedores.dto.ProveedorDto;
import cl.marfuego.ms_proveedores.model.Proveedor;
import cl.marfuego.ms_proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        log.info("--> [GET] Solicitud para listar todos los proveedores recibida.");
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para obtener proveedor con ID: {}", id);
        // Si no existe, el Service lanza ErrorNoEncontrado y el Capturador responde 404
        return ResponseEntity.ok(proveedorService.buscarProveedorPorId(id));
    }

    @PostMapping
    public ResponseEntity<Proveedor> guardarProveedor(@Valid @RequestBody ProveedorDto dto) {
        log.info("--> [POST] Solicitud para guardar un nuevo proveedor recibida.");
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        Proveedor nuevoProveedor = proveedorService.guardarProveedor(dto);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar proveedor con ID: {}", id);
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }



}
