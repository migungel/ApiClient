package com.prueba.ApiClient.DTO.Movimientos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientosRequest {

    private String numeroCuenta;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
}
