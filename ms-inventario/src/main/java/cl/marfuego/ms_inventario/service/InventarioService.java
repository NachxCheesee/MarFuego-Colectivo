package cl.marfuego.ms_inventario.service;

import cl.marfuego.ms_inventario.DTO.InventarioDTO;
import cl.marfuego.ms_inventario.client.LocalClient;
import cl.marfuego.ms_inventario.model.Inventario;
import cl.marfuego.ms_inventario.repository.InventarioRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventariorepository;
    private final LocalClient localClient;


    public InventarioService(InventarioRepository inventariorepository, LocalClient localClient) {
        this.inventariorepository = inventariorepository;
        this.localClient = localClient;
    }

    public List<Inventario> listarInventario (){
        log.info("[Service] Solicitando todo el inventario a la base de datos");
        return inventariorepository.findAll();
    }

    public Inventario buscarPorId(Long id){
        log.info("[Service] Buscando producto por Id: {}",id);
        return inventariorepository.findById(id).orElseThrow(() -> {
            log.warn("[Service] ⚠️ Fallo: No se logró encontrar el producto con id: {}",id);
            return new RuntimeException("no se encontro el producto con la id:"+ id);
        });
    }

    public List<Inventario> listarPorLocal(Long local_id){
        log.info("[Service] Buscando inventario para local: {}",local_id);
        List<Inventario> lista = inventariorepository.findByLocal_id(local_id);
        log.info("[Service] Encontrado {} productos para local: {}",lista.size(),local_id);
        return lista;
    }

    public Inventario guardarInventario (InventarioDTO inventariodto){
        log.info("[Service] Iniciando validacion para registrar un nuevo producto en inventario: {}",inventariodto.getNombre());
        validarLocalExiste(inventariodto.getLocal_id());

        Inventario inventario = new Inventario();
        cargarDatos(inventario, inventariodto);
        Inventario guardado = inventariorepository.save(inventario);
        log.info("[Service] Producto guardado exitosamente con id: {}",guardado.getId());
        return guardado;
    }

    public Inventario actualizarInventario(Long id, InventarioDTO inventariodto){
        log.info("[Service] Iniciando actualización del producto con id: {}",id);
        validarLocalExiste(inventariodto.getLocal_id());

        Inventario inventario = buscarPorId(id);
        cargarDatos(inventario, inventariodto);
        Inventario actualizado = inventariorepository.save(inventario);
        log.info("[Service] Producto con Id {} actualizado exitosamente",id);
        return actualizado;
    }

    public void eliminarInventario(Long id){
        log.info("[Service] Eliminando producto con id: {}",id);
        Inventario inventario = buscarPorId(id);
        inventariorepository.delete(inventario);
        log.info("[Service] Producto con id {} eliminado exitosamente",id);
    }

    private void cargarDatos(Inventario inventario, InventarioDTO inventarioDTO) {
        log.debug("[Service] Cargando datos del DTO al modelo: nombre={}, local_id={}", inventarioDTO.getNombre(), inventarioDTO.getLocal_id());
        inventario.setNombre(inventarioDTO.getNombre());
        inventario.setLocal_id(inventarioDTO.getLocal_id());
    }

    private void validarLocalExiste(Long local_id) {
        log.info("[Service] Validando existencia del local con id: {}",local_id);
        try {
            Object local = localClient.obtenerLocalPorId(local_id);

            if (local == null) {
                log.warn("[Service] ⚠️ Error de validación: El local con id {} no existe, cancelando operación",local_id);
                throw new RuntimeException("Error: El local con la ID " + local_id + " NO existe. Guardado cancelado.");
            }
            log.info("[Service] Validación exitosa: el local con id {} existe",local_id);
        } catch (FeignException.NotFound e) {
            log.warn("[Service] Error de validación: el local con id {} no existe (feign 404). cancelando la operacion",local_id);
            throw new RuntimeException("Error: El local con la ID " + local_id + " NO existe. Guardado cancelado.");
        } catch (FeignException e) {
            log.error("[Service] Error de comunicación con el servicio de locales para el id {}: {}",local_id,e.getMessage(),e);
            throw new RuntimeException("Error de comunicación con el servicio de locales: " + e.getMessage());
        }
    }
}