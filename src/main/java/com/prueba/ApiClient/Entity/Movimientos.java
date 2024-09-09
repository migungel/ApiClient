package com.prueba.ApiClient.Entity;

import com.prueba.ApiClient.DTO.Movimientos.MovimientosRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="movimientos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimientos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    //@JoinColumn(name = "numeroCuenta", referencedColumnName = "numeroCuenta")
    @Column(name = "numeroCuenta", nullable = false)
    private String numeroCuenta;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "tipoMovimiento", nullable = false, length = 50)
    private String tipoMovimiento;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "saldo", nullable = false)
    private Double saldo;

    public Movimientos(MovimientosRequest data) {
        this.numeroCuenta = data.getNumeroCuenta();
        this.fecha = LocalDateTime.now();
        this.tipoMovimiento = data.getTipoMovimiento();
        this.valor = data.getValor();
    }
}
