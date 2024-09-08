//package com.prueba.ApiClient.util.Tests;
//
//import com.prueba.ApiClient.Entity.Cuenta;
//import com.prueba.ApiClient.Repository.CuentaRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.util.Optional;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@SpringJUnitConfig
//public class CuentaRepositoryTest {
//
//    @Autowired
//    private CuentaRepository cuentaRepository;
//
//    @Test
//    public void whenFindByNumeroCuenta_thenReturnCuenta() {
//        Cuenta cuenta = new Cuenta();
//        cuenta.setNumeroCuenta("123456");
//        cuentaRepository.save(cuenta);
//
//        Optional<Cuenta> foundCuenta = cuentaRepository.findByNumeroCuenta("123456");
//
//        assertThat(foundCuenta).isPresent();
//        assertThat(foundCuenta.get().getNumeroCuenta()).isEqualTo("123456");
//    }
//}
