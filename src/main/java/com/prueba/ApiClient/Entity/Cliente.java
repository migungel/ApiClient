package com.prueba.ApiClient.Entity;

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
    private String estado;
}
