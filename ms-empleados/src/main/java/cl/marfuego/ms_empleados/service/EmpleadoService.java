package cl.marfuego.ms_empleados.service;

import cl.marfuego.ms_empleados.dto.EmpleadoDto;
import cl.marfuego.ms_empleados.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_empleados.model.Empleado;
import cl.marfuego.ms_empleados.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listarEmpleados() {

        return empleadoRepository.findAll();

    }

    public Empleado buscarEmpleadoPorId(Long id) {

        return empleadoRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró el empleado con ID: " + id));

    }

    public Empleado guardarEmpleado(EmpleadoDto dto) {

        // Convertimos el dto en una Entidad Empleado
        Empleado empleado = new Empleado();
        empleado.setNombreCompleto(dto.getNombreCompleto());
        empleado.setCargo(dto.getCargo());
        empleado.setLocalId(dto.getLocalId());

        return empleadoRepository.save(empleado);

    }


    public void eliminarEmpleado(Long id) {

        if (!empleadoRepository.existsById(id)) {
            throw new ErrorNoEncontrado("No se puede eliminar: El empleado " + id + " no existe.");
        }
        empleadoRepository.deleteById(id);


    }



}
