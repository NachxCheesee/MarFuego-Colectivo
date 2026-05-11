package cl.marfuego.ms_locales.service;


import cl.marfuego.ms_locales.dto.LocalDto;
import cl.marfuego.ms_locales.dto.MesaDto;
import cl.marfuego.ms_locales.enums.EstadoMesa;
import cl.marfuego.ms_locales.exception.custom.ErrorNoEncontrado;
import cl.marfuego.ms_locales.model.Local;
import cl.marfuego.ms_locales.model.Mesa;
import cl.marfuego.ms_locales.repository.LocalRepository;
import cl.marfuego.ms_locales.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocalService {

    private final LocalRepository localRepository;

    private final MesaRepository mesaRepository;

    public LocalService(LocalRepository localRepository, MesaRepository mesaRepository) {
        this.localRepository = localRepository;
        this.mesaRepository = mesaRepository;
    }

    public List<Local> listarLocales() {

        return localRepository.findAll();

    }

    public Local buscarLocalPorId(Long id) {

        return localRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró el local con ID: " + id));

    }

    public Local guardarLocal(LocalDto dto) {

        // Convertimos el dto en una Entidad Local
        Local local = new Local();
        local.setNombre(dto.getNombre());
        local.setDireccion(dto.getDireccion());
        local.setCiudad(dto.getCiudad());

        return localRepository.save(local);

    }

    public boolean existeLocalPorId(Long id) {

        return localRepository.existsById(id);


    }

    public void eliminarLocal(Long id) {

        if (!localRepository.existsById(id)) {
            throw new ErrorNoEncontrado("No se puede eliminar: El local " + id + " no existe.");
        }
        localRepository.deleteById(id);


    }


    // Funciones para mesa

    public List<Mesa> listarMesas() {

        return mesaRepository.findAll();

    }

    public Mesa buscarMesaPorId(Long id) {

        return mesaRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró la mesa con ID: " + id));

    }

    public Mesa guardarMesa(MesaDto dto) {

        // 1. Buscamos el local al que pertenece la mesa (VITAL)
        Local local = localRepository.findById(dto.getLocalId()).orElseThrow(() -> new ErrorNoEncontrado("No se pudo crear la mesa: Local " + dto.getLocalId() + " no existe."));

        // 2. Creamos la mesa y le "enchufamos" el local completo
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(dto.getNumeroMesa());
        mesa.setCapacidad(dto.getCapacidad());
        mesa.setLocal(local); // Aquí se crea la relación en la BD

        // El estado se asigna automáticamente como LIBRE en la entidad Mesa
        if (dto.getEstado() != null) {
            mesa.setEstado(dto.getEstado());
        }

        return mesaRepository.save(mesa);

    }


    public void eliminarMesa(Long id) {

        if (!mesaRepository.existsById(id)) {
            throw new ErrorNoEncontrado("No se puede eliminar: La mesa " + id + " no existe.");
        }
        mesaRepository.deleteById(id);
    }


    public List<Mesa> obtenerMesaEstadoPorLocal(Long localId, EstadoMesa estado) {

        return mesaRepository.findByLocalIdAndEstado(localId, estado);
    }


    public Mesa cambiarEstadoMesa(Long id, EstadoMesa nuevoEstado) {
        Mesa mesa = mesaRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se puede cambiar el estado: Mesa " + id + " no existe."));
        mesa.setEstado(nuevoEstado);
        return mesaRepository.save(mesa);
    }


}
