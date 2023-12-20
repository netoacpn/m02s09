package tech.devinhouse.veiculos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

  @Id
  private String placa;

  @Enumerated(EnumType.STRING)
  private TipoVeiculo tipo;

  private String nome;

  private Integer anoFabricacao;

  private String cor;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "veiculo")
  private List<Multa> multas;

  public Veiculo(String placa, TipoVeiculo tipo, String nome, Integer anoFabricacao, String cor) {
    this.placa = placa;
    this.tipo = tipo;
    this.nome = nome;
    this.anoFabricacao = anoFabricacao;
    this.cor = cor;
  }

  public boolean temMultas(){
    return this.multas != null && !this.multas.isEmpty();
  }
}

