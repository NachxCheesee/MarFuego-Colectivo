package cl.marfuego.ms_empleados.repository;

import cl.marfuego.ms_empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository <Empleado, Long> {
}
