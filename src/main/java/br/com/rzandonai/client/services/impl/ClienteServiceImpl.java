package br.com.rzandonai.client.services.impl;

import java.util.Date;
import java.util.Optional;

import br.com.rzandonai.client.entities.Cliente;
import br.com.rzandonai.client.repositories.ClienteRepository;
import br.com.rzandonai.client.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;


@Service
@CacheConfig(cacheNames = "clienteCache")
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Cacheable(cacheNames = "clientes")
    @Override
    public Page<Cliente> findAll(Specification spec, Pageable pageable) {
        return  this.clienteRepository.findAll(spec, pageable);
    }

    @CacheEvict(cacheNames = "clientes", allEntries = true)
    @Override
    public Cliente add(Cliente cliente) {
        if(cliente.getId() != null){
            throw new ValidationException("Só é possivel inserir um registro sem id");
        }
        cliente.setDataCriacao(new Date());
        return this.clienteRepository.save(cliente);
    }

    @CacheEvict(cacheNames = "clientes", allEntries = true)
    @Override
    public Cliente update(Cliente cliente) {
        Optional<Cliente> optCliente = this.clienteRepository.findById(cliente.getId());
        if (!optCliente.isPresent()) {
            throw new ValidationException("Só é possivel atualizar um registro existente");
        }
        Cliente repCliente = optCliente.get();
        repCliente.setNome(cliente.getNome());
        repCliente.setNumero(cliente.getNumero());
        repCliente.setComplemento(cliente.getComplemento());
        repCliente.setCep(cliente.getCep());
        repCliente.setCidade(cliente.getCidade());
        repCliente.setPais(cliente.getPais());
        repCliente.setCpf(cliente.getCpf());
        repCliente.setDataAtualizacao(new Date());
        return this.clienteRepository.save(repCliente);
    }

    @Caching(evict = {@CacheEvict(cacheNames = "cliente", key = "#id"),
            @CacheEvict(cacheNames = "clientes", allEntries = true)})
    @Override
    public void delete(long id) {
        this.clienteRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "cliente", key = "#id", unless = "#result == null")
    @Override
    public Cliente getClienteById(long id) {
        return this.clienteRepository.findById(id).orElse(null);
    }

}