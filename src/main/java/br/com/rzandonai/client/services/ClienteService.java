package br.com.rzandonai.client.services;

import br.com.rzandonai.client.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ClienteService {

    Cliente add(Cliente Client);

    Cliente update(Cliente Client);

    void delete(long id);

    Cliente getClienteById(long id);

    Page<Cliente> findAll(Specification spec, Pageable pageable);
}
