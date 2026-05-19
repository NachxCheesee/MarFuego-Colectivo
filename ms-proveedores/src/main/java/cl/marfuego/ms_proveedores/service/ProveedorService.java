package cl.marfuego.ms_proveedores.service;

import cl.marfuego.ms_proveedores.dto.ProveedorDto;
import cl.marfuego.ms_proveedores.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_proveedores.model.Proveedor;
import cl.marfuego.ms_proveedores.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }


    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor buscarProveedorPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ErrorNoEncontrado("No se encontró el proveedor con ID: " + id));
    }

    public Proveedor guardarProveedor(ProveedorDto dto) {

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

        if (!proveedorRepository.existsById(id)) {
            throw new ErrorNoEncontrado("No se puede eliminar: El proveedor " + id + " no existe.");
        }
        proveedorRepository.deleteById(id);
    }
}

