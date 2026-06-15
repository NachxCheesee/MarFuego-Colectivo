package cl.marfuego.ms_empleados.controller;


import cl.marfuego.ms_empleados.service.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import cl.marfuego.ms_empleados.dto.EmpleadoDto;
import cl.marfuego.ms_empleados.dto.EmpleadoRespuestaDto;
import cl.marfuego.ms_empleados.model.Empleado;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }


    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        log.info("--> [GET] Solicitud para listar todos los empleados recibida.");
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para buscar empleado con ID: {}", id);
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<EmpleadoRespuestaDto> verDetalle(@PathVariable Long id) {
        log.info("--> [GET] Solicitud de detalle completo para el empleado con ID: {}", id);
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoDetallado(id));
    }


    @PostMapping
    public ResponseEntity<Empleado> guardar(@Valid @RequestBody EmpleadoDto dto) {
        log.info("--> [POST] Solicitud para registrar un nuevo empleado recibida.");
        return new ResponseEntity<>(empleadoService.guardarEmpleado(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoDto dto) {
        log.info("--> [PUT] Solicitud para actualizar al empleado con ID: {}", id);
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar empleado con ID: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}