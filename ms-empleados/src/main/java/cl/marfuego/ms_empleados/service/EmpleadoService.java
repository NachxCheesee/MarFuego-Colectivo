package cl.marfuego.ms_empleados.service;

import cl.marfuego.ms_empleados.client.LocalClient;
import cl.marfuego.ms_empleados.dto.EmpleadoDto;
import cl.marfuego.ms_empleados.dto.EmpleadoRespuestaDto;
import cl.marfuego.ms_empleados.dto.LocalDto;
import cl.marfuego.ms_empleados.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_empleados.model.Empleado;
import cl.marfuego.ms_empleados.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final LocalClient localClient;

    public EmpleadoService(EmpleadoRepository empleadoRepository, LocalClient localClient) {
        this.empleadoRepository = empleadoRepository;
        this.localClient = localClient;
    }

    public List<Empleado> listarEmpleados() {

        return empleadoRepository.findAll();

    }

    public Empleado buscarEmpleadoPorId(Long id) {

        return empleadoRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró el empleado con ID: " + id));

    }

    public EmpleadoRespuestaDto obtenerEmpleadoDetallado(Long id) {

        Empleado empleado = buscarEmpleadoPorId(id);

        LocalDto infoLocal = localClient.obtenerDatosDeLocal(empleado.getLocalId());

        EmpleadoRespuestaDto respuesta = new EmpleadoRespuestaDto();
        respuesta.setId(empleado.getId());
        respuesta.setNombreCompleto(empleado.getNombreCompleto());
        respuesta.setCargo(empleado.getCargo());
        respuesta.setFechaIngreso(empleado.getFechaIngreso());


        respuesta.setLocal(infoLocal);

        return respuesta;
    }


    public Empleado guardarEmpleado(EmpleadoDto dto) {

        try {
            localClient.obtenerDatosDeLocal(dto.getLocalId());
        } catch (Exception e) {

            throw new ErrorNoEncontrado("No se puede contratar: El local " + dto.getLocalId() + " no existe.");
        }

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


    public Empleado actualizarEmpleado(Long id, EmpleadoDto dto) {

        Empleado empleadoExistente = buscarEmpleadoPorId(id);

        try {
            localClient.obtenerDatosDeLocal(dto.getLocalId());
        } catch (Exception e) {
            throw new ErrorNoEncontrado("No se puede actualizar: El local " + dto.getLocalId() + " no existe.");
        }

        empleadoExistente.setNombreCompleto(dto.getNombreCompleto());
        empleadoExistente.setCargo(dto.getCargo());
        empleadoExistente.setLocalId(dto.getLocalId());

        return empleadoRepository.save(empleadoExistente);


    }


}
