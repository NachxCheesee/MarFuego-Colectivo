package com.example.producto.controller;

import com.example.producto.dto.ErrorDto;
import com.example.producto.dto.MovimientoStockDto;
import com.example.producto.dto.ProductoDto;
import com.example.producto.model.Producto;
import com.example.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(
        name = "productos",
        description = "administracion de productos disponibles en el local"
)
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    @Operation(
            summary = "obtiene todos los productos del local",
            description = "retorna la lista completa de productos que fueron ingresados en el local"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "consulta exitosa"),
            @ApiResponse(responseCode = "500",
                    description = "Error interno")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos(){
        return ResponseEntity.ok(productoService.listarProductos());
    }
    @Operation(summary = "Obtener un producto por ID", description = "Busca un producto específico utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El formato del ID ingresado en la URL es inválido (debe ser un número)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ProductoIdInvalido",
                                    value = "{\"status\": 400, \"mensaje\": \"El formato del ID ingresado en la URL es inválido (debe ser un número)\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ningún producto con el ID ingresado",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ProductoNoEncontrado",
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
                                    name = "Error500ServidorProducto",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:35:00\"}"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id){
        // Si no existe, el Service lanza ErrorNoEncontrado y el Capturador responde 404
        return ResponseEntity.ok(productoService.buscaPorId(id));
    }
    @Operation(summary = "Registrar un nuevo empleado", description = "Agrega un nuevo empleado utilizando un formato de archivo JSON.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto registrado de forma exitosa"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación en los campos del DTO o formato JSON inválido",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ValidacionProducto",
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
                                    name = "Error500ServidorRegistrarProducto",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T16:45:00\"}"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@Valid @RequestBody ProductoDto dto){
        Producto nuevoProducto = productoService.guardarProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);

    }
    @Operation(summary = "Actualizar un producto por ID", description = "Actualiza por completo los datos de un producto existente basándose en su ID y un JSON válido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado con éxito"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de ID inválido o error de validación en los campos del cuerpo JSON",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400ActualizarProducto",
                                    value = "{\"status\": 400, \"mensaje\": \"Formato de ID inválido o error de validación en los campos del cuerpo JSON\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se puede actualizar: El producto no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404ActualizarProducto",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede actualizar: El producto no existe en los registros\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorActualizarProducto",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:50:00\"}"
                            )
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDto dto){
        Producto productoActualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(productoActualizado);
    }
    @Operation(summary = "Eliminar un producto por ID", description = "Elimina un producto específico del sistema utilizando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado de forma exitosa (Sin contenido)"),
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
                    description = "No se puede eliminar: El producto no existe en los registros",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404DeleteProducto",
                                    value = "{\"status\": 404, \"mensaje\": \"No se puede eliminar: El producto no existe en los registros\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500ServidorDeleteProducto",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T18:55:00\"}"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        productoService.eliminaProducto(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }
    //metodos para gestion del stock
    //metodo para agregar cantidad al stock
    @Operation(summary = "Agregar stock a un producto", description = "Incrementa la cantidad física en stock de un producto específico (Entrada de inventario).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock agregado con éxito al producto"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación (ej. cantidad negativa o formato inválido)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400AgregarStock",
                                    value = "{\"status\": 400, \"mensaje\": \"La cantidad debe ser un número mayor a cero\", \"timestamp\": \"2026-06-17T19:00:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El producto al que se intenta agregar stock no existe",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404AgregarStock",
                                    value = "{\"status\": 404, \"mensaje\": \"El producto no existe en los registros\", \"timestamp\": \"2026-06-17T19:00:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500AgregarStock",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T19:00:00\"}"
                            )
                    )
            )
    })
    @PatchMapping("/{id}/stock/agregar")
    public ResponseEntity<Producto> agregarStock(@PathVariable Long id, @RequestBody MovimientoStockDto movimiento) {

        // Llamamos al servicio pasando el ID y el objeto con la cantidad
        Producto productoActualizado = productoService.agregarStock(id, movimiento);

        // Devolvemos el producto (Model) que sí incluye el ID en el JSON de respuesta
        return ResponseEntity.ok(productoActualizado);
    }
    @Operation(summary = "Quitar o descontar stock de un producto", description = "Disminuye la cantidad física en stock de un producto específico (Salida de inventario).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock descontado con éxito del producto"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación (ej. cantidad a descontar es mayor al stock disponible o formato inválido)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error400QuitarStock",
                                    value = "{\"status\": 400, \"mensaje\": \"Stock insuficiente para realizar el descuento\", \"timestamp\": \"2026-06-17T19:15:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El producto del que se intenta descontar stock no existe",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error404QuitarStock",
                                    value = "{\"status\": 404, \"mensaje\": \"El producto no existe en los registros\", \"timestamp\": \"2026-06-17T19:15:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno inesperado en el servidor",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "Error500QuitarStock",
                                    value = "{\"status\": 500, \"mensaje\": \"Ocurrió un error inesperado en el sistema MarFuego\", \"timestamp\": \"2026-06-17T19:15:00\"}"
                            )
                    )
            )
    })
    //metodo para quitar stock
    @PatchMapping("/{id}/stock/quitar")
    public ResponseEntity<Producto> quitarStock(@PathVariable Long id, @RequestBody MovimientoStockDto movimiento){
        Producto productoActualizado = productoService.quitarStock(id, movimiento);
        return ResponseEntity.ok(productoActualizado);
    }
}
