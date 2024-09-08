package com.prueba.ApiClient.Repository;

import com.prueba.ApiClient.Entity.Cliente;
import com.prueba.ApiClient.Entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsById(Long id);

}
