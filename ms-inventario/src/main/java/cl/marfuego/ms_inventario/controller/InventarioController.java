package cl.marfuego.ms_inventario.controller;

import cl.marfuego.ms_inventario.DTO.ErrorDto;
import cl.marfuego.ms_inventario.DTO.InventarioDTO;
import cl.marfuego.ms_inventario.model.Inventario;
import cl.marfuego.ms_inventario.service.InventarioService;
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
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con la gestión del inventario de productos de MarFuego")
public class InventarioController {

    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de inventario", description = "Busca y agrupa todos los productos registrados en el inventario del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta de inventario exitosa"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error inesperado en el servidor al procesar la lista de inventario",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ListarInventario",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-23T10:00:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<Inventario>> listarInventario() {
        log.info("--> [GET] Solicitud para listar todo el inventario recibida.");
        return ResponseEntity.ok(inventarioService.listarInventario());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico en el inventario utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido (debe ser un número)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400InventarioIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido (debe ser un número)\", \"timestamp\": \"2026-06-23T10:05:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ningún producto con el ID ingresado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404InventarioNoEncontrado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se encontró el producto con la ID ingresada\", \"timestamp\": \"2026-06-23T10:05:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorInventario",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-23T10:05:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Inventario> buscarPorId(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para buscar producto con ID: {}", id);
        return ResponseEntity.ok(inventarioService.buscarPorId(id));
    }

    @GetMapping("/local/{local_id}")
    @Operation(summary = "Obtener inventario por local", description = "Lista todos los productos de inventario asociados a un local específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de productos del local obtenida con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID de local ingresado en la URL es inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400InventarioLocalIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID de local ingresado en la URL es inválido (debe ser un número)\", \"timestamp\": \"2026-06-23T10:10:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorInventarioLocal",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-23T10:10:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<Inventario>> listarPorLocal(@PathVariable Long local_id) {
        log.info("--> [GET] Solicitud para listar inventario del local ID: {}", local_id);
        return ResponseEntity.ok(inventarioService.listarPorLocal(local_id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo inventario", description = "Agrega un nuevo inventario utilizando un formato de archivo JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventario registrado de forma exitosa"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los campos del DTO o formato JSON inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ValidacionInventario",
                                    value = "{\"status\": 400, \"mensaje\": \"Error de validación en los campos del DTO o formato JSON inválido\", \"timestamp\": \"2026-06-23T10:15:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El local asociado al inventario no existe",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404InventarioLocalNoExiste",
                                    value = "{\"status\": 404, \"mensaje\": \"Error: El local con la ID especificada NO existe. Operación cancelada.\", \"timestamp\": \"2026-06-23T10:15:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorRegistrarInventario",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-23T10:15:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Inventario> guardarInventario(@Valid @RequestBody InventarioDTO inventariodto) {
        log.info("--> [POST] Solicitud para registrar un nuevo inventario recibida.");
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.guardarInventario(inventariodto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un inventario por ID", description = "Actualiza por completo los datos de un inventario existente basándose en su ID y un JSON válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventario actualizado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de ID inválido o error de validación en los campos del cuerpo JSON",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ActualizarInventario",
                                    value = "{\"status\": 400, \"mensaje\": \"Formato de ID inválido o error de validación en los campos del cuerpo JSON\", \"timestamp\": \"2026-06-23T10:20:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede actualizar: El inventario no existe o el local asociado no existe",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ActualizarInventario",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede actualizar: El inventario no existe o el local asociado no existe\", \"timestamp\": \"2026-06-23T10:20:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorActualizarInventario",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-23T10:20:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long id, @Valid @RequestBody InventarioDTO inventariodto) {
        log.info("--> [PUT] Solicitud para actualizar inventario con ID: {}", id);
        return ResponseEntity.ok(inventarioService.actualizarInventario(id, inventariodto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un inventario por ID", description = "Elimina un inventario específico utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventario eliminado de forma exitosa (Sin contenido)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400DeleteInventario",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido\", \"timestamp\": \"2026-06-23T10:25:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede eliminar: El inventario no existe",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404DeleteInventario",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede eliminar: El producto no existe en el inventario\", \"timestamp\": \"2026-06-23T10:25:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorDeleteInventario",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-23T10:25:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar inventario con ID: {}", id);
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}