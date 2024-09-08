package com.prueba.ApiClient.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Persona {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", updatable = false, nullable = false, unique = true)
//    private Long id;

//    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    private String genero;

    private Integer edad;

//    @Column(name="identificacion", nullable = false, length = 20)
    private String identificacion;

    private String direccion;

    private String telefono;

}
