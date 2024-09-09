package com.prueba.ApiClient.DTO.Cuenta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaRequest {

    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
}
