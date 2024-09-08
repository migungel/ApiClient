package com.prueba.ApiClient.Controller;

import com.prueba.ApiClient.Entity.Cuenta;
import com.prueba.ApiClient.Service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
@Tag(name="Cuentas", description = "Operaciones relacionadas con la gestión de cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Operation(summary = "Obtener todas las cuentas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de cuentas obtenido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron cuentas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Cuenta>> obtenerTodasCuentas() {
        List<Cuenta> cuentas = cuentaService.getAllCuenta();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una cuenta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.getCuentaByid(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

//    @GetMapping("/numCnt/{numeroCuenta}")
//    public ResponseEntity<Cuenta> getCuenta(@PathVariable String numeroCuenta) {
//        Cuenta cuenta = cuentaService.getCuentaByNumero(numeroCuenta);
//        return new ResponseEntity<>(cuenta, HttpStatus.OK);
//    }

    @Operation(summary = "Crear una nueva cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaService.createCuenta(cuenta);
        return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una cuenta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta detallesCuenta) {
        Cuenta cuentaActualizada = cuentaService.updateCuenta(id, detallesCuenta);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @Operation(summary = "Eliminar una cuenta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
