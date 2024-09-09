package com.prueba.ApiClient.Entity;

import com.prueba.ApiClient.DTO.Cliente.ClienteRequest;
import com.prueba.ApiClient.DTO.Movimientos.MovimientosRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clienteid", unique = true, nullable = false, updatable = false)
    private Long clienteid;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "estado")
    private Boolean estado;

    public Cliente(ClienteRequest data) {
        this.setNombre(data.getNombre());
        this.setGenero(data.getGenero());
        this.setEdad(data.getEdad());
        this.setIdentificacion(data.getIdentificacion());
        this.setDireccion(data.getDireccion());
        this.setTelefono(data.getTelefono());
        this.contrasena = data.getContrasena();
        this.estado = data.getEstado();
    }
}
