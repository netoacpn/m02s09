package tech.devinhouse.veiculos.dto;

import lombok.Data;

@Data
public class UsuarioResponse {

    private Integer id;

    private String nome;

    private String email;

    private String Role;

}
