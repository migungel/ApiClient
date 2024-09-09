package com.prueba.ApiClient.Service;

import com.prueba.ApiClient.DTO.Cuenta.CuentaRequest;
import com.prueba.ApiClient.Entity.Cuenta;
import com.prueba.ApiClient.Entity.Movimientos;
import com.prueba.ApiClient.Repository.CuentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> getAllCuenta(){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        if (cuentas.isEmpty()) {
            throw new NoSuchElementException("No se encontraron cuentas");
        }
        return cuentas;
    }

    public Cuenta getCuentaByid(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada con el ID: " + id));
    }

//    public Cuenta getCuentaByNumero(String numeroCuenta) {
//        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
//                .orElseThrow(() -> new EntityNotFoundException("Cuenta no encontrada con el número: " + numeroCuenta));
//    }


    public Cuenta createCuenta(CuentaRequest cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no puede ser nula");
        }

        if (cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta con el número de cuenta: " + cuenta.getNumeroCuenta());
        }

        Cuenta cuentaEntity = new Cuenta(cuenta);

        try {
            return cuentaRepository.save(cuentaEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error al guardar la cuenta: " + e.getMessage(), e);
        }
    }

    public Cuenta updateCuenta(Long id, CuentaRequest detallesCuenta) {
        if (!cuentaRepository.existsById(id)) {
            throw new NoSuchElementException("Cuenta no encontrada con el ID: " + id);
        }

        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada con el ID: " + id));

        cuentaExistente.setNumeroCuenta(detallesCuenta.getNumeroCuenta());
        cuentaExistente.setTipoCuenta(detallesCuenta.getTipoCuenta());
        cuentaExistente.setSaldoInicial(detallesCuenta.getSaldoInicial());
        cuentaExistente.setEstado(detallesCuenta.getEstado());

        return cuentaRepository.save(cuentaExistente);
    }

    public void deleteCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new NoSuchElementException("Cuenta no encontrada con el ID: " + id);
        }

        cuentaRepository.deleteById(id);
    }
}
