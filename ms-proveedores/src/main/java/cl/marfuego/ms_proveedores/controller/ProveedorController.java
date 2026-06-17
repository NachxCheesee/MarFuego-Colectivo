package cl.marfuego.ms_proveedores.controller;


import cl.marfuego.ms_proveedores.dto.ErrorDto;
import cl.marfuego.ms_proveedores.dto.ProveedorDto;
import cl.marfuego.ms_proveedores.model.Proveedor;
import cl.marfuego.ms_proveedores.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Operaciones relacionadas con la gestión y administración de los proveedores de MarFuego")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private static final Logger log = LoggerFactory.getLogger(ProveedorController.class);
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de proveedores", description = "Busca y retorna todos los proveedores registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta de proveedores exitosa"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error inesperado en el servidor al procesar la lista",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ListarProveedores",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:52:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        log.info("--> [GET] Solicitud para listar todos los proveedores recibida.");
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Busca un proveedor específico utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL no es válido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ProveedorIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL no es válido\", \"timestamp\": \"2026-06-17T18:53:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ningún proveedor con el ID ingresado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ProveedorNoEncontrado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se encontró ningún proveedor con el ID ingresado\", \"timestamp\": \"2026-06-17T18:53:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorProveedor",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:53:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para obtener proveedor con ID: {}", id);
        // Si no existe, el Service lanza ErrorNoEncontrado y el Capturador responde 404
        return ResponseEntity.ok(proveedorService.buscarProveedorPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo proveedor", description = "Crea un nuevo proveedor en el sistema a partir de los datos del JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Proveedor registrado de forma exitosa"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los atributos del DTO o formato JSON inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ValidacionProveedor",
                                    value = "{\"status\": 400, \"mensaje\": \"Error de validación en los atributos del DTO o formato JSON inválido\", \"timestamp\": \"2026-06-17T18:54:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorRegistrarProveedor",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:54:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Proveedor> guardarProveedor(@Valid @RequestBody ProveedorDto dto) {
        log.info("--> [POST] Solicitud para guardar un nuevo proveedor recibida.");
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        Proveedor nuevoProveedor = proveedorService.guardarProveedor(dto);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un proveedor por ID", description = "Elimina de forma permanente un proveedor del sistema basándose en su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Proveedor eliminado de forma exitosa (Sin contenido)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL no es válido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400DeleteProveedor",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL no es válido\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede eliminar: El proveedor no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404DeleteProveedor",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede eliminar: El proveedor no existe en los registros\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorDeleteProveedor",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar proveedor con ID: {}", id);
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }



}
