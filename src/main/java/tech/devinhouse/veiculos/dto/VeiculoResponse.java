package tech.devinhouse.veiculos.dto;

import lombok.Data;
import tech.devinhouse.veiculos.models.TipoVeiculo;

import java.util.List;

@Data
public class VeiculoResponse {

  private String plca;

  private TipoVeiculo tipo;

  private String nome;

  private Integer anoFabricacao;

  private String cor;

  private List<MultaResponse> multas;
}
