package com.prueba.ApiClient.Repository;

import com.prueba.ApiClient.Entity.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {
    boolean existsById(Long id);

    //@Query("SELECT m FROM Movimientos m WHERE m.numeroCuenta = :numeroCuenta ORDER BY m.fecha DESC")
    @Query(value = "SELECT TOP 1 * FROM movimientos WHERE numero_cuenta = :numeroCuenta ORDER BY fecha DESC", nativeQuery = true)
    Optional<Movimientos> findLatestByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);
}
