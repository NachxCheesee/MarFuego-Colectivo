package cl.marfuego.ms_empleados.service;

import cl.marfuego.ms_empleados.client.LocalClient;
import cl.marfuego.ms_empleados.dto.EmpleadoDto;
import cl.marfuego.ms_empleados.dto.EmpleadoRespuestaDto;
import cl.marfuego.ms_empleados.dto.LocalDto;
import cl.marfuego.ms_empleados.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_empleados.model.Empleado;
import cl.marfuego.ms_empleados.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    private static final Logger log = LoggerFactory.getLogger(EmpleadoService.class);
    private final EmpleadoRepository empleadoRepository;
    private final LocalClient localClient;

    public EmpleadoService(EmpleadoRepository empleadoRepository, LocalClient localClient) {
        this.empleadoRepository = empleadoRepository;
        this.localClient = localClient;
    }

    public List<Empleado> listarEmpleados() {
        log.info("[Service] Solicitando todos los empleados a la base de datos.");
        return empleadoRepository.findAll();

    }

    public Empleado buscarEmpleadoPorId(Long id) {
        log.info("[Service] Buscando empleado por ID: {}", id);
        return empleadoRepository.findById(id).orElseThrow(() -> {
            log.warn("[Service] ⚠️ Fallo: No se encontró el empleado con ID: {}", id);
            return new ErrorNoEncontrado("No se encontró el empleado con ID: " + id);
        });
    }

    public EmpleadoRespuestaDto obtenerEmpleadoDetallado(Long id) {
        log.info("[Service] Procesando detalle completo para el empleado ID: {}. Solicitando datos adicionales al ms_locales.", id);
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
        log.info("[Service] Iniciando validación para registrar al empleado: {}", dto.getNombreCompleto());
        try {
            localClient.obtenerDatosDeLocal(dto.getLocalId());
        } catch (Exception e) {
            log.warn("[Service] ⚠️ Error de validación: El local ID {} no existe. Cancelando contratación.", dto.getLocalId());
            throw new ErrorNoEncontrado("No se puede contratar: El local " + dto.getLocalId() + " no existe.");
        }

        Empleado empleado = new Empleado();
        empleado.setNombreCompleto(dto.getNombreCompleto());
        empleado.setCargo(dto.getCargo());
        empleado.setLocalId(dto.getLocalId());

        return empleadoRepository.save(empleado);

    }


    public void eliminarEmpleado(Long id) {
        log.info("[Service] Intentando eliminar empleado con ID: {}", id);
        if (!empleadoRepository.existsById(id)) {
            log.warn("[Service] ⚠️ Fallo al eliminar: El empleado {} no existe en los registros.", id);
            throw new ErrorNoEncontrado("No se puede eliminar: El empleado " + id + " no existe.");
        }
        empleadoRepository.deleteById(id);


    }


    public Empleado actualizarEmpleado(Long id, EmpleadoDto dto) {
        log.info("[Service] Iniciando actualización del empleado con ID: {}", id);
        Empleado empleadoExistente = buscarEmpleadoPorId(id);

        try {
            localClient.obtenerDatosDeLocal(dto.getLocalId());
        } catch (Exception e) {
            log.warn("[Service] ⚠️ Error al actualizar: El local ID {} no existe.", dto.getLocalId());
            throw new ErrorNoEncontrado("No se puede actualizar: El local " + dto.getLocalId() + " no existe.");
        }

        empleadoExistente.setNombreCompleto(dto.getNombreCompleto());
        empleadoExistente.setCargo(dto.getCargo());
        empleadoExistente.setLocalId(dto.getLocalId());

        return empleadoRepository.save(empleadoExistente);


    }


}
