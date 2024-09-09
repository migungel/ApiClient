package com.prueba.ApiClient.Entity;

import com.prueba.ApiClient.DTO.Cuenta.CuentaRequest;
import com.prueba.ApiClient.DTO.Movimientos.MovimientosRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false)
    private String numeroCuenta;

    @Column(name = "tipoCuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldoInicial", nullable = false)
    private Double saldoInicial;

    @Column(name = "estado")
    private Boolean estado;

    public Cuenta(CuentaRequest data) {
        this.numeroCuenta = data.getNumeroCuenta();
        this.tipoCuenta = data.getTipoCuenta();
        this.saldoInicial = data.getSaldoInicial();
        this.estado = data.getEstado();
    }

}
