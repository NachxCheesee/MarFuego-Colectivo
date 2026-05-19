package cl.marfuego.ms_proveedores.repository;

import cl.marfuego.ms_proveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository <Proveedor, Long> {
}
