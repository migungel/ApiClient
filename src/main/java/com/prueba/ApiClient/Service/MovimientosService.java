package com.prueba.ApiClient.Service;

import com.prueba.ApiClient.DTO.Movimientos.MovimientosRequest;
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

    public Movimientos createMovimiento(MovimientosRequest movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("Movimiento no puede ser nulo");
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

        //movimiento.setSaldo(saldoActual);
        Movimientos movEntity = new Movimientos(movimiento);
        movEntity.setSaldo(saldoActual);

        try {
            return movimientosRepository.save(movEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error al guardar el movimiento: " + e.getMessage(), e);
        }
    }

    public Movimientos updateMovimiento(Long movimientoId, Movimientos detallesMovimiento) {
        Movimientos movimientoExistente = movimientosRepository.findById(movimientoId)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con el ID: " + movimientoId));

        movimientoExistente.setTipoMovimiento(detallesMovimiento.getTipoMovimiento());
        movimientoExistente.setValor(detallesMovimiento.getValor());
        movimientoExistente.setNumeroCuenta(detallesMovimiento.getNumeroCuenta());
        movimientoExistente.setFecha(detallesMovimiento.getFecha());
        movimientoExistente.setSaldo(detallesMovimiento.getSaldo());

        return movimientosRepository.save(movimientoExistente);
    }

    public void deleteMovimiento(Long id) {
        if (!movimientosRepository.existsById(id)) {
            throw new NoSuchElementException("Movimiento no encontrado con el ID: " + id);
        }

        movimientosRepository.deleteById(id);
    }
}
