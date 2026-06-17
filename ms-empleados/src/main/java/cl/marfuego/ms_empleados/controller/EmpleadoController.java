package cl.marfuego.ms_empleados.controller;


import cl.marfuego.ms_empleados.dto.ErrorDto;
import cl.marfuego.ms_empleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Tag(name = "Empleados", description = "Operaciones relacionadas con la gestión y administración del personal de MarFuego")
public class EmpleadoController {
    private static final Logger log = LoggerFactory.getLogger(EmpleadoController.class);
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de empleados", description = "Busca y agrupa todos los empleados registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta de empleados exitosa"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error inesperado en el servidor al procesar la lista de empleados",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ListarEmpleados",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:30:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<Empleado>> listar() {
        log.info("--> [GET] Solicitud para listar todos los empleados recibida.");
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Busca un empleado específico utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido (debe ser un número)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400EmpleadoIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido (debe ser un número)\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ningún empleado con el ID ingresado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404EmpleadoNoEncontrado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se encontró ningún empleado con el ID ingresado\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorEmpleado",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Empleado> buscarPorId(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para buscar empleado con ID: {}", id);
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener detalle completo de un empleado", description = "Busca la información extendida y formateada de un empleado por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle del empleado obtenido con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400EmpleadoDetalleInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido\", \"timestamp\": \"2026-06-17T16:40:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el empleado solicitado para ver el detalle",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404EmpleadoDetalleNoEncontrado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se encontró el empleado solicitado para ver el detalle\", \"timestamp\": \"2026-06-17T16:40:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorEmpleadoDetalle",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:40:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<EmpleadoRespuestaDto> verDetalle(@PathVariable Long id) {
        log.info("--> [GET] Solicitud de detalle completo para el empleado con ID: {}", id);
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoDetallado(id));
    }


    @PostMapping
    @Operation(summary = "Registrar un nuevo empleado", description = "Agrega un nuevo empleado utilizando un formato de archivo JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado registrado de forma exitosa"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los campos del DTO o formato JSON inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ValidacionEmpleado",
                                    value = "{\"status\": 400, \"mensaje\": \"Error de validación en los campos del DTO o formato JSON inválido\", \"timestamp\": \"2026-06-17T16:45:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorRegistrarEmpleado",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:45:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Empleado> guardar(@Valid @RequestBody EmpleadoDto dto) {
        log.info("--> [POST] Solicitud para registrar un nuevo empleado recibida.");
        return new ResponseEntity<>(empleadoService.guardarEmpleado(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado por ID", description = "Actualiza por completo los datos de un empleado existente basándose en su ID y un JSON válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de ID inválido o error de validación en los campos del cuerpo JSON",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ActualizarEmpleado",
                                    value = "{\"status\": 400, \"mensaje\": \"Formato de ID inválido o error de validación en los campos del cuerpo JSON\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede actualizar: El empleado no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ActualizarEmpleado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede actualizar: El empleado no existe en los registros\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorActualizarEmpleado",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoDto dto) {
        log.info("--> [PUT] Solicitud para actualizar al empleado con ID: {}", id);
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado por ID", description = "Elimina un empleado específico del sistema utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado de forma exitosa (Sin contenido)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400DeleteEmpleado",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede eliminar: El empleado no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404DeleteEmpleado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede eliminar: El empleado no existe en los registros\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorDeleteEmpleado",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar empleado con ID: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}