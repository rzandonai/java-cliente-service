package br.com.rzandonai.client.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Cliente", schema = "public")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotBlank
    private String nome;

    private String rua;

    private Integer numero;

    private String complemento;

    private String cidade;

    private String cep;

    private String pais;
    @JsonIgnore
    private Date dataAtualizacao;

    @JsonIgnore
    private Date dataCriacao;

    @CPF
    @Column(unique = true)
    private String cpf;

}

