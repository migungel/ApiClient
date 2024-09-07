package com.prueba.ApiClient.Controller;

import com.prueba.ApiClient.Entity.Persona;
import com.prueba.ApiClient.Repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public List<Persona> obtenerTodasPersonas() {
        return personaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersona(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        System.out.println("######################################## mensajes de personas ###############");
        System.out.println(persona.isPresent());
        System.out.println(persona.isEmpty());
        if (persona.isPresent()) {
            return ResponseEntity.ok(persona.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Persona crearPersona (@RequestBody Persona persona) {
        return personaRepository.save(persona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona personaUpdate) {
        Optional<Persona> personaExist = personaRepository.findById(id);
        if (personaExist.isPresent()) {
            Persona persona = personaExist.get();
            persona.setNombre(personaUpdate.getNombre());
            persona.setGenero(personaUpdate.getGenero());
            persona.setEdad(personaUpdate.getEdad());
            persona.setIdentificacion(personaUpdate.getIdentificacion());
            persona.setDireccion(personaUpdate.getDireccion());
            persona.setTelefono(personaUpdate.getTelefono());
            Persona personaNew = personaRepository.save(persona);
            return ResponseEntity.ok(personaNew);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isPresent()) {
            personaRepository.delete(persona.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
