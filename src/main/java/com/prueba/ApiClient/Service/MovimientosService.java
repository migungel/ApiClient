package com.prueba.ApiClient.Service;

import com.prueba.ApiClient.Entity.Cuenta;
import com.prueba.ApiClient.Entity.Movimientos;
import com.prueba.ApiClient.Repository.CuentaRepository;
import com.prueba.ApiClient.Repository.MovimientosRepository;
import com.prueba.ApiClient.util.Exceptions.SaldoNoDisponibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovimientosService {

    @Autowired
    private MovimientosRepository movimientosRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimientos> getAllMovimientos() {
        List<Movimientos> movimientos = movimientosRepository.findAll();
        if (movimientos.isEmpty()) {
            throw new NoSuchElementException("No se encontraron movimientos");
        }
        return movimientos;
    }

    public Movimientos getMovimientoById(Long id) {
        return movimientosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con el ID: " + id));
    }

    public Movimientos createMovimiento(Movimientos movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("Movimiento no puede ser nulo");
        }

        if (movimiento.getId() != null && movimientosRepository.existsById(movimiento.getId())) {
            throw new IllegalArgumentException("Ya existe un movimiento con el ID: " + movimiento.getId());
        }

        if (movimiento.getNumeroCuenta() == null || movimiento.getNumeroCuenta().isEmpty()) {
            throw new IllegalArgumentException("Número de cuenta no puede ser nulo o vacío");
        }

        String tipoMovimiento = movimiento.getTipoMovimiento();
        if (!"DEPOSITO".equals(tipoMovimiento) && !"RETIRO".equals(tipoMovimiento)) {
            throw new IllegalArgumentException("Tipo de movimiento inválido");
        }

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        Optional<Movimientos> ultimoMovimiento = movimientosRepository.findLatestByNumeroCuenta(movimiento.getNumeroCuenta());
        double saldoActual = ultimoMovimiento
                .map(Movimientos::getSaldo)
                .orElse(cuenta.getSaldoInicial());

        if ("DEPOSITO".equals(tipoMovimiento)) {
            saldoActual += movimiento.getValor();
        //} else if ("RETIRO".equals(tipoMovimiento)) {
        } else {
            if (movimiento.getValor() > saldoActual) {
                throw new SaldoNoDisponibleException("Saldo no disponible");
            }
            saldoActual -= movimiento.getValor();
        }

        movimiento.setSaldo(saldoActual);

        try {
            return movimientosRepository.save(movimiento);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error al guardar el movimiento: " + e.getMessage(), e);
        }
    }

    public Movimientos updateMovimiento(Long movimientoId, Movimientos detallesMovimiento) {
        Movimientos movimientoExistente = movimientosRepository.findById(movimientoId)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con el ID: " + movimientoId));

        movimientoExistente.setTipoMovimiento(detallesMovimiento.getTipoMovimiento());
        movimientoExistente.setValor(detallesMovimiento.getValor());
        movimientoExistente.setSaldo(detallesMovimiento.getSaldo());
        movimientoExistente.setFecha(detallesMovimiento.getFecha());
        movimientoExistente.setNumeroCuenta(detallesMovimiento.getNumeroCuenta());

        return movimientosRepository.save(movimientoExistente);
    }

    public void deleteMovimiento(Long id) {
        if (!movimientosRepository.existsById(id)) {
            throw new NoSuchElementException("Movimiento no encontrado con el ID: " + id);
        }

        movimientosRepository.deleteById(id);
    }
}
