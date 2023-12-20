package tech.devinhouse.veiculos.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import tech.devinhouse.veiculos.models.TipoVeiculo;

@Data
public class VeiculoRequest {

  @NotEmpty(message = "{required.field}")
  private String placa;

  @NotNull(message = "{required.field}")
  private TipoVeiculo tipo;

  @NotEmpty(message = "{required.field}")
  private String nome;

  @NotNull(message = "{required.field}")
  @Positive(message = "{invalid.field}")
  private Integer anoFabricacao;

  @NotEmpty(message = "{required.field}")
  private String cor;
}
