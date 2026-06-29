package cl.marfuego.ms_pedidos.controller;

import cl.marfuego.ms_pedidos.dto.ErrorDto;
import cl.marfuego.ms_pedidos.dto.PedidoDTO;
import cl.marfuego.ms_pedidos.model.Pedido;
import cl.marfuego.ms_pedidos.service.PedidoService;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos de MarFuego")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de pedidos", description = "Busca y agrupa todos los pedidos registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta de pedidos exitosa"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error inesperado en el servidor al procesar la lista de pedidos",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ListarPedidos",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:30:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<Pedido>> listar() {
        log.info("--> [GET] Solicitud para listar todos los pedidos recibida.");
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Busca un pedido específico utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido (debe ser un número)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400PedidoIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido (debe ser un número)\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ningún pedido con el ID ingresado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404PedidoNoEncontrado",
                                    value = "{\"status\": 404, \"mensaje\": \"No se encontró ningún pedido con el ID ingresado\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorPedido",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para buscar pedido con ID: {}", id);
        return ResponseEntity.ok(pedidoService.buscarPedidoPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pedido", description = "Agrega un nuevo pedido utilizando un formato de archivo JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido registrado de forma exitosa"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los campos del DTO o formato JSON inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ValidacionPedido",
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
                                    name = "Error500ServidorRegistrarPedido",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:45:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Pedido> guardar(@Valid @RequestBody PedidoDTO dto) {
        log.info("--> [POST] Solicitud para registrar un nuevo pedido recibida.");
        Pedido nuevo = pedidoService.guardarPedido(dto);
        log.info("<-- [POST] Pedido creado con ID: {}", nuevo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido por ID", description = "Actualiza por completo los datos de un pedido existente basándose en su ID y un JSON válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de ID inválido o error de validación en los campos del cuerpo JSON",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ActualizarPedido",
                                    value = "{\"status\": 400, \"mensaje\": \"Formato de ID inválido o error de validación en los campos del cuerpo JSON\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede actualizar: El pedido no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ActualizarPedido",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede actualizar: El pedido no existe en los registros\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorActualizarPedido",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoDTO dto) {
        log.info("--> [PUT] Solicitud para actualizar el pedido con ID: {}", id);
        Pedido actualizado = pedidoService.actualizarPedido(id, dto);
        log.info("<-- [PUT] Pedido con ID {} actualizado correctamente.", id);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido por ID", description = "Elimina un pedido específico del sistema utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado de forma exitosa (Sin contenido)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400DeletePedido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede eliminar: El pedido no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404DeletePedido",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede eliminar: El pedido no existe en los registros\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorDeletePedido",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            )
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar pedido con ID: {}", id);
        pedidoService.eliminarPedido(id);
        log.info("<-- [DELETE] Pedido con ID {} eliminado correctamente.", id);
        return ResponseEntity.noContent().build();
    }
}