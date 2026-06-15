package cl.marfuego.ms_proveedores.service;

import cl.marfuego.ms_proveedores.dto.ProveedorDto;
import cl.marfuego.ms_proveedores.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_proveedores.model.Proveedor;
import cl.marfuego.ms_proveedores.repository.ProveedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    private static final Logger log = LoggerFactory.getLogger(ProveedorService.class);
    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }


    public List<Proveedor> listarProveedores() {
        log.info("[Service] Solicitando todos los proveedores a la base de datos.");
        return proveedorRepository.findAll();
    }

    public Proveedor buscarProveedorPorId(Long id) {
        log.info("[Service] Buscando proveedor por ID: {}", id);
        return proveedorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[Service] ⚠️ Fallo: No se encontró el proveedor con ID: {}", id);
                    return new ErrorNoEncontrado("No se encontró el proveedor con ID: " + id);
                });
    }

    public Proveedor guardarProveedor(ProveedorDto dto) {
        log.info("[Service] Procesando el registro del nuevo proveedor: {}", dto.getNombre());
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setTipoInsumo(dto.getTipoInsumo());
        proveedor.setTipoPersona(dto.getTipoPersona());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        proveedor.setZona(dto.getZona());
        proveedor.setEstado(dto.getEstado());

        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        log.info("[Service] Intentando eliminar proveedor con ID: {}", id);
        if (!proveedorRepository.existsById(id)) {
            log.warn("[Service] ⚠️ Fallo al eliminar: El proveedor {} no existe en los registros.", id);
            throw new ErrorNoEncontrado("No se puede eliminar: El proveedor " + id + " no existe.");
        }
        proveedorRepository.deleteById(id);
    }
}

