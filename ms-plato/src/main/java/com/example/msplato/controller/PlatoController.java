package com.example.msplato.controller;

import com.example.msplato.dto.ErrorDto;
import com.example.msplato.dto.PlatoDto;
import com.example.msplato.model.Plato;
import com.example.msplato.service.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")

@Tag(
     name = "platos",
     description = "administracion de platos cocinados en el local"
)
public class PlatoController {
    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;

    }
    @Operation(
            summary = "obtiene todos los platos del local",
            description = "retorna la lista completa de platos que fueron ingresados en el local"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "consulta exitosa"),
            @ApiResponse(responseCode = "500",
                    description = "Error interno")
    })
    @GetMapping
    public ResponseEntity<List<Plato>> listarPlatos(){
        return ResponseEntity.ok(platoService.listarPlatos());

    }
    @Operation(summary = "Obtener empleado por ID", description = "Busca un plato específico utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato encontrado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido (debe ser un número)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400PlatoIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido (debe ser un número)\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ningún plato con el ID ingresado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404PlatoNoEncontrado",
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
                                    name = "Error500ServidorPlato",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Plato> obtenerPlatoPorId(@PathVariable Long id){
        return ResponseEntity.ok(platoService.buscarPlatoPorId(id));

    }
    @Operation(summary = "Registrar un nuevo plato", description = "Agrega un nuevo plato utilizando un formato de archivo JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plato registrado de forma exitosa"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los campos del DTO o formato JSON inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ValidacionPlato",
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
                                    name = "Error500ServidorRegistrarPlato",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:45:00\"}"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Plato> guardarPlato(@Valid @RequestBody PlatoDto dto) throws BadRequestException {
        Plato platoNuevo = platoService.guardarPlato(dto);
        return new ResponseEntity<>(platoNuevo, HttpStatus.CREATED);
    }
    @Operation(summary = "Actualizar un plato por ID", description = "Actualiza por completo los datos de un plato existente basándose en su ID y un JSON válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plato actualizado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de ID inválido o error de validación en los campos del cuerpo JSON",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ActualizarPlato",
                                    value = "{\"status\": 400, \"mensaje\": \"Formato de ID inválido o error de validación en los campos del cuerpo JSON\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede actualizar: El plato no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ActualizarPlato",
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
                                    name = "Error500ServidorActualizarPlato",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Plato> actualizarPlato(@PathVariable Long id, @Valid @RequestBody PlatoDto dto) throws BadRequestException {
        Plato platoActualizado = platoService.actualizarPlato(id, dto);
        return ResponseEntity.ok(platoActualizado);
    }
    @Operation(summary = "Eliminar un empleado por ID", description = "Elimina un plato específico del sistema utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Plato eliminado de forma exitosa (Sin contenido)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400DeletePlato",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede eliminar: El plato no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404DeletePlato",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede eliminar: El plato no existe en los registros\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorDeleteplato",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Long id){
        platoService.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }

}
