package cl.marfuego.ms_locales.controller;

import cl.marfuego.ms_locales.dto.LocalDto;
import cl.marfuego.ms_locales.dto.MesaDto;
import cl.marfuego.ms_locales.enums.EstadoMesa;
import cl.marfuego.ms_locales.model.Local;
import cl.marfuego.ms_locales.model.Mesa;
import cl.marfuego.ms_locales.service.LocalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locales")
public class LocalController {

    private static final Logger log = LoggerFactory.getLogger(LocalController.class);
    private final LocalService localService;

    public LocalController(LocalService localService) {

        this.localService = localService;
    }

    @GetMapping
    public ResponseEntity<List<Local>> listarLocales() {
        log.info("--> [GET] Solicitud para listar todos los locales recibida.");
        List<Local> locales = localService.listarLocales();
        log.info("<-- [GET] Retornando lista con {} locales.", locales.size());
        return ResponseEntity.ok(locales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> obtenerLocal(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para obtener local con ID: {}", id);
        // Si no existe, el Service lanza ErrorNoEncontrado y el Capturador responde 404
        return ResponseEntity.ok(localService.buscarLocalPorId(id));
    }

    @PostMapping
    public ResponseEntity<Local> guardarLocal(@Valid @RequestBody LocalDto dto) {
        log.info("--> [POST] Solicitud para guardar un nuevo local recibida.");
        // Si llegamos aquí el DTO ya está validado y no hubo errores
        Local nuevoLocal = localService.guardarLocal(dto);
        return new ResponseEntity<>(nuevoLocal, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocal(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar local con ID: {}", id);
        localService.eliminarLocal(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 (Éxito sin contenido)
    }

    // FUNCIONES PARA MESAS ---------------------------------------------

    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> listarMesas() {
        log.info("--> [GET] Solicitud para listar todas las mesas recibida.");
        return ResponseEntity.ok(localService.listarMesas());
    }

    // Buscar mesa por ID
    @GetMapping("/mesas/{id}")
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable Long id) {
        log.info("--> [GET] Solicitud para obtener mesa con ID: {}", id);
        // El service lanza la excepción si no existe, el capturador hace el resto.
        return ResponseEntity.ok(localService.buscarMesaPorId(id));
    }

    @PostMapping("/mesas")
    public ResponseEntity<Mesa> crearMesa(@Valid @RequestBody MesaDto dto) {
        log.info("--> [POST] Solicitud para crear una nueva mesa recibida.");
        return new ResponseEntity<>(localService.guardarMesa(dto), HttpStatus.CREATED);
    }

    //  Eliminar mesa
    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id) {
        log.info("--> [DELETE] Solicitud para eliminar mesa con ID: {}", id);
        localService.eliminarMesa(id);
        return ResponseEntity.noContent().build(); // 204 No Content: Éxito sin datos que devolver
    }

    @GetMapping("/mesas/filtro/{localId}")
    public ResponseEntity<List<Mesa>> listarMesasDisponibles(@PathVariable Long localId, @RequestParam EstadoMesa estado) {
        log.info("--> [GET] Solicitud para filtrar mesas por local ID: {} y estado: {}", localId, estado);
        List<Mesa> mesas = localService.obtenerMesaEstadoPorLocal(localId, estado);
        return ResponseEntity.ok(mesas);
    }

    // 4. Cambiar solo el estado de la mesa (Actualización parcial)
    // Ejemplo: PATCH /api/locales/mesas/1/estado?nuevoEstado=OCUPADA
    @PatchMapping("/mesas/{id}/estado")
    public ResponseEntity<Mesa> actualizarEstadoMesa(@PathVariable Long id, @RequestParam EstadoMesa nuevoEstado) {
        log.info("--> [PATCH] Solicitud para cambiar estado de mesa ID: {} a {}", id, nuevoEstado);
        Mesa mesaActualizada = localService.cambiarEstadoMesa(id, nuevoEstado);
        return ResponseEntity.ok(mesaActualizada);
    }

}
