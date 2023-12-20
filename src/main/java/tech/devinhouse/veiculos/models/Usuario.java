package tech.devinhouse.veiculos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String nome;

  private String email;

  private String senha;


}



  /*

    Classe Usuario (Tabela Usuarios):

    id (Integer) - incremental

    nome (String)

    email (String)

    senha (String)

    role (Enum Role com valores ADMIN e USUARIO)*/
