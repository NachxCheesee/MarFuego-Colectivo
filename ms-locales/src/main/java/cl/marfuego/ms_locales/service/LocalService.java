package cl.marfuego.ms_locales.service;


import cl.marfuego.ms_locales.enums.EstadoMesa;
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

    // listar buscar guardar eliminar
    // guardar mesa eliminar mesa

    public List<Local> listarLocales() {

        return localRepository.findAll();

    }

    public Local buscarLocalPorId(Long id) {

        Optional<Local> local = localRepository.findById(id);


        return local.orElse(null);

    }

    public Local guardarLocal(Local local) {

        return localRepository.save(local);


    }

    public boolean existeLocalPorId(Long id) {

        return localRepository.existsById(id);


    }

    public void eliminarLocal(Long id) {

        localRepository.deleteById(id);


    }


    // Funciones para mesa

    public List<Mesa> listarMesas() {

        return mesaRepository.findAll();

    }

    public Mesa buscarMesaPorId(Long id) {

        Optional<Mesa> mesa = mesaRepository.findById(id);


        return mesa.orElse(null);

    }

    public Mesa guardarMesa(Mesa mesa) {

        return mesaRepository.save(mesa);


    }


    public void eliminarMesa(Long id) {

        mesaRepository.deleteById(id);

    }


    public boolean existeMesaPorId(Long id) {

        return mesaRepository.existsById(id);


    }

    public List<Mesa> obtenerDisponiblesPorLocal(Long localId, EstadoMesa estado) {

        return mesaRepository.findByLocalIdAndEstado(localId, estado);
    }


    public Mesa cambiarEstadoMesa(Long id, EstadoMesa nuevoEstado) {
        Mesa mesa = buscarMesaPorId(id);
        if (mesa != null) {
            mesa.setEstado(nuevoEstado);
            return mesaRepository.save(mesa);
        }
        return null;
    }


}
