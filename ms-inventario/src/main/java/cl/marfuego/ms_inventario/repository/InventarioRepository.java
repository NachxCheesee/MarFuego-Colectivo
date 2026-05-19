package cl.marfuego.ms_inventario.repository;

import cl.marfuego.ms_inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    @Query("SELECT i FROM Inventario i WHERE i.local_id = :local_id")
    List<Inventario> findByLocal_id(@Param("local_id") Long local_id);
}
