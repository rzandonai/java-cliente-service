package br.com.rzandonai.client.controllers;

import br.com.rzandonai.client.entities.Cliente;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.rzandonai.client.services.ClienteService;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    /**
     * Método responsável por pesquisar cliente usando Query Strings.
     * Utilize os parametros do cliente para buscar id nome etc...
     *
     * @param pageable pageable
     */

    @GetMapping(value = "/clientes")
    public ResponseEntity<Page<Cliente>> getClientes(@SearchSpec Specification<Cliente> specs,
                                                     @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(this.clienteService.findAll(specs, pageable));
    }

    @GetMapping(value = "/clientes/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.clienteService.getClienteById(id));
    }

    @PostMapping(value = "/clientes")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente Cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteService.add(Cliente));
    }

    @PutMapping(value = "/clientes/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") Long id,@RequestBody Cliente Cliente) {
        Cliente.setId(id);
        Cliente updated = this.clienteService.update(Cliente);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(value = "/clientes/{id}")
    public ResponseEntity<Cliente> deleteClienteById(@PathVariable("id") Long id) {
        this.clienteService.delete(id);
        return ResponseEntity.ok().build();
    }
}

