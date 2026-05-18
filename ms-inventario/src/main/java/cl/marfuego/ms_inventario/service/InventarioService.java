package cl.marfuego.ms_inventario.service;

import cl.marfuego.ms_inventario.DTO.InventarioDTO;
import cl.marfuego.ms_inventario.client.LocalClient;
import cl.marfuego.ms_inventario.model.Inventario;
import cl.marfuego.ms_inventario.repository.InventarioRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository inventariorepository;
    private final LocalClient localClient;


    public InventarioService(InventarioRepository inventariorepository, LocalClient localClient) {
        this.inventariorepository = inventariorepository;
        this.localClient = localClient;
    }

    public List<Inventario> listarInventario (){
        return inventariorepository.findAll();
    }

    public Inventario buscarPorId(Long id){
        return inventariorepository.findById(id).orElseThrow(() -> new RuntimeException("no se encontro el producto con la id:"+ id));
    }

    public List<Inventario> listarPorLocal(Long local_id){
        return inventariorepository.findByLocal_id(local_id);
    }

    public Inventario guardarInventario (InventarioDTO inventariodto){
        validarLocalExiste(inventariodto.getLocal_id());

        Inventario inventario = new Inventario();
        cargarDatos(inventario, inventariodto);
        return inventariorepository.save(inventario);
    }

    public Inventario actualizarInventario(Long id, InventarioDTO inventariodto){
        validarLocalExiste(inventariodto.getLocal_id());

        Inventario inventario = buscarPorId(id);
        cargarDatos(inventario, inventariodto);
        return inventariorepository.save(inventario);
    }

    public void eliminarInventario(Long id){
        Inventario inventario = buscarPorId(id);
        inventariorepository.delete(inventario);
    }

    private void cargarDatos(Inventario inventario, InventarioDTO inventarioDTO) {
        inventario.setNombre(inventarioDTO.getNombre());
        inventario.setLocal_id(inventarioDTO.getLocal_id());
    }

    private void validarLocalExiste(Long local_id) {
        try {
            Object local = localClient.obtenerLocalPorId(local_id);

            if (local == null) {
                throw new RuntimeException("Error: El local con la ID " + local_id + " NO existe. Guardado cancelado.");
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Error: El local con la ID " + local_id + " NO existe. Guardado cancelado.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con el servicio de locales: " + e.getMessage());
        }
    }
}