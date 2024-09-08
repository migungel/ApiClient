package com.prueba.ApiClient.Controller;

import com.prueba.ApiClient.Entity.Cuenta;
import com.prueba.ApiClient.Entity.Movimientos;
import com.prueba.ApiClient.Service.MovimientosService;
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
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Operaciones relacionadas con la gestión de movimientos")
public class MovimientosController {

    @Autowired
    private MovimientosService movimientosService;

    @Operation(summary = "Obtener todos los movimientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de movimientos obtenido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron movimientos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Movimientos>> obtenerTodosMovimientos() {
        List<Movimientos> movimientos = movimientosService.getAllMovimientos();
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un movimiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> obtenerMovimientoId(@PathVariable Long id) {
        Movimientos movimiento = movimientosService.getMovimientoById(id);
        return new ResponseEntity<>(movimiento, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo movimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Movimientos> crearMovimiento(@RequestBody Movimientos movimiento) {
        Movimientos nuevoMovimiento = movimientosService.createMovimiento(movimiento);
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un movimiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimientos detallesMovimiento) {
        Movimientos movimientoActualizado = movimientosService.updateMovimiento(id, detallesMovimiento);
        return ResponseEntity.ok(movimientoActualizado);
    }

    @Operation(summary = "Eliminar un movimiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientosService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
