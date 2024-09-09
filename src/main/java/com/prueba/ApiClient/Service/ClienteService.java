package com.prueba.ApiClient.Service;

import com.prueba.ApiClient.DTO.Cliente.ClienteRequest;
import com.prueba.ApiClient.Entity.Cliente;
import com.prueba.ApiClient.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        //return clienteRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            throw new NoSuchElementException("No se encontraron clientes");
        }
        return clientes;
    }

    public Cliente getClienteById(Long clienteid) {
        return clienteRepository.findById(clienteid)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con el ID: " + clienteid));
    }

    public Cliente createCliente(ClienteRequest cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser nulo");
        }

        Cliente clienteEntity = new Cliente(cliente);

        try {
            return clienteRepository.save(clienteEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error al guardar el cliente: " + e.getMessage(), e);
        }
    }

    public Cliente updateCliente(Long clienteid, ClienteRequest detallesCliente) {
        if (!clienteRepository.existsById(clienteid)) {
            throw new NoSuchElementException("Cliente no encontrado con el ID: " + clienteid);
        }

        Cliente clienteExistente = clienteRepository.findById(clienteid)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con el ID: " + clienteid));

        clienteExistente.setContrasena(detallesCliente.getContrasena());
        clienteExistente.setEstado(detallesCliente.getEstado());
        clienteExistente.setNombre(detallesCliente.getNombre());
        clienteExistente.setGenero(detallesCliente.getGenero());
        clienteExistente.setEdad(detallesCliente.getEdad());
        clienteExistente.setIdentificacion(detallesCliente.getIdentificacion());
        clienteExistente.setDireccion(detallesCliente.getDireccion());
        clienteExistente.setTelefono(detallesCliente.getTelefono());

        return clienteRepository.save(clienteExistente);
    }

    public void deleteCliente(Long clienteid) {
        if (!clienteRepository.existsById(clienteid)) {
            throw new NoSuchElementException("Cliente no encontrado con el ID: " + clienteid);
        }

        clienteRepository.deleteById(clienteid);
    }
}
