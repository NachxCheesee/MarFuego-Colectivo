package cl.marfuego.ms_inventario.controller;

import cl.marfuego.ms_inventario.DTO.InventarioDTO;
import cl.marfuego.ms_inventario.model.Inventario;
import cl.marfuego.ms_inventario.repository.InventarioRepository;
import cl.marfuego.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    private final InventarioService inventarioservice;

    public InventarioController(InventarioService inventarioservice) {
        this.inventarioservice = inventarioservice;
    }
    @GetMapping
    public ResponseEntity<List<Inventario>> listarInventario(){
        return ResponseEntity.ok(inventarioservice.listarInventario());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(inventarioservice.buscarPorId(id));
    }
    @GetMapping("/local/{local_id}")
    public ResponseEntity<List<Inventario>> listarPorLocal(@PathVariable Long local_id){
        return ResponseEntity.ok(inventarioservice.listarPorLocal(local_id));
    }
    @PostMapping
    public ResponseEntity<Inventario> guardarInventario(@Valid @RequestBody InventarioDTO inventariodto){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioservice.guardarInventario(inventariodto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long id, @Valid @RequestBody InventarioDTO inventariodto){
        return ResponseEntity.ok(inventarioservice.actualizarInventario(id, inventariodto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id){
        inventarioservice.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}
