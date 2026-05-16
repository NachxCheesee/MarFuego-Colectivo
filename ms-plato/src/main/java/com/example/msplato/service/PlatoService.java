package com.example.msplato.service;

import com.example.msplato.client.LocalClient;
import com.example.msplato.client.ProductoClient;
import com.example.msplato.dto.PlatoDto;
import com.example.msplato.dto.ProductoDto;
import com.example.msplato.enums.TipoDeProducto;
import com.example.msplato.exception.custom.ErrorNoEncontrado;
import com.example.msplato.model.Plato;
import com.example.msplato.repository.PlatoRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlatoService {
    private final PlatoRepository platoRepository;
    private final ProductoClient productoClient;
    private final LocalClient localClient;

    public PlatoService(PlatoRepository platoRepository, ProductoClient productoClient, LocalClient localClient) {
        this.platoRepository = platoRepository;
        this.productoClient = productoClient;
        this.localClient = localClient;
    }

    public List<Plato> listarPlatos (){
        return platoRepository.findAll();
    }
    public Plato buscarPlatoPorId(Long id) {

        return platoRepository.findById(id).orElseThrow(() -> new ErrorNoEncontrado("No se encontró el plato: " + id));

    }
    public Plato guardarPlato(PlatoDto dto) throws BadRequestException {
        // 1. VALIDAMOS QUE EL LOCAL EXISTA
        try {
            localClient.obtenerLocalPorId(dto.getLocalId());
        } catch (Exception e) {
            throw new ErrorNoEncontrado("El local con ID " + dto.getLocalId() + " no existe.");
        }
        //iniciamos una variable para calcular el costo de los ingredientes qu vamos a usar
        double costoTotalIngredientes = 0;

        // Recorremos la lista de IDs que vienen en el DTO
        for (Long productoId : dto.getProductoIds()) {

            // 1. Llamada via Feign al micro de productos
            ProductoDto producto = productoClient.obtenerProductosPorId(productoId);

            // 2. VALIDACIÓN CRÍTICA: ¿Es un ingrediente?
            if (producto.getTipo() != TipoDeProducto.INGREDIENTE) {
                throw new BadRequestException("El producto '" + producto.getNombre() +
                        "' no es un ingrediente. No puedes añadir " + producto.getTipo() + " a un plato");
            }

            // 3. Aprovechamos de sumar el costo para la Regla R5 (Markup 300%)
            costoTotalIngredientes += producto.getPrecio();
        }

        //validacion del 300%
        if (dto.getPrecioVenta() < (costoTotalIngredientes * 3)) {
            // Usamos System.err para que salga resaltado en la consola de IntelliJ
            System.err.println("⚠️ ADVERTENCIA DE NEGOCIO (R5): El plato '" + dto.getNombre() +
                    "' tiene un precio de $" + dto.getPrecioVenta() +
                    ". Para cumplir el margen del 300%, debería venderse al menos a $" +
                    (costoTotalIngredientes * 3) + ". (Guardando de todos modos...)");
        }
        // cargamos los datos
        Plato plato = new Plato();
        plato.setNombre(dto.getNombre());
        plato.setLocalId(dto.getLocalId());
        plato.setPrecioVenta(dto.getPrecioVenta());
        plato.setDisponible(dto.isDisponible());
        plato.setProductoId(dto.getProductoIds());
        //guardamos
        return platoRepository.save(plato);
    }
    public Plato actualizarPlato(Long id, PlatoDto dto) throws BadRequestException {
        Plato platoExistente = platoRepository.findById(id)
                .orElseThrow(()-> new ErrorNoEncontrado(("El plato con ID " + id + " no existe.")));
        double costoTotalIngredientes = 0;
        // Recorremos la lista de IDs que vienen en el DTO
        for (Long productoId : dto.getProductoIds()) {

            // 1. Llamada via Feign al micro de productos
            ProductoDto producto = productoClient.obtenerProductosPorId(productoId);

            // 2. VALIDACIÓN CRÍTICA: ¿Es un ingrediente?
            if (producto.getTipo() != TipoDeProducto.INGREDIENTE) {
                throw new BadRequestException("El producto '" + producto.getNombre() +
                        "' no es un ingrediente. No puedes añadir " + producto.getTipo() + " a un plato.");
            }

            // 3. Aprovechamos de sumar el costo para la Regla R5 (Markup 300%)
            costoTotalIngredientes += producto.getPrecio();
        }


        if (dto.getPrecioVenta() < (costoTotalIngredientes * 3)) {
            // Usamos System.err para que salga resaltado en la consola de IntelliJ
            System.err.println("⚠️ ADVERTENCIA DE NEGOCIO (R5): El plato '" + dto.getNombre() +
                    "' tiene un precio de $" + dto.getPrecioVenta() +
                    ". Para cumplir el margen del 300%, debería venderse al menos a $" +
                    (costoTotalIngredientes * 3) + ". (Guardando de todos modos...)");
        }

        platoExistente.setNombre(dto.getNombre());
        platoExistente.setLocalId(dto.getLocalId());
        platoExistente.setPrecioVenta(dto.getPrecioVenta());
        platoExistente.setDisponible(dto.isDisponible());
        platoExistente.setProductoId(dto.getProductoIds());

        return platoRepository.save(platoExistente);
    }
    public boolean existePlatoPorId(Long id) {

        return platoRepository.existsById(id);


    }

    public void eliminarPlato(Long id) {

        if (!platoRepository.existsById(id)) {
            throw new ErrorNoEncontrado("No se puede eliminar: El plato con el " + id + " no existe.");
        }
        platoRepository.deleteById(id);


    }
}

