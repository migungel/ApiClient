package com.prueba.ApiClient.Repository;

import com.prueba.ApiClient.Entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    boolean existsById(Long id);

    @Query(value = "SELECT * FROM dbo.cuentas c WHERE c.numero_cuenta = :numeroCuenta", nativeQuery = true)
    Optional<Cuenta> findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);
}
