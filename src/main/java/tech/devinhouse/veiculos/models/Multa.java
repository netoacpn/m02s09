package tech.devinhouse.veiculos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "multas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Multa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String local;

  private String motivo;

  private Float valor;

  @ManyToOne
  @JoinColumn(name = "placa", referencedColumnName = "placa")
  private Veiculo veiculo;

  public Multa(String local, String motivo, Float valor, Veiculo veiculo) {
    this.local = local;
    this.motivo = motivo;
    this.valor = valor;
    this.veiculo = veiculo;
  }
}
