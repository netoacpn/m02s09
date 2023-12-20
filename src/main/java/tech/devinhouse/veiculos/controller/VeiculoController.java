package tech.devinhouse.veiculos.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.veiculos.dto.MultaRequest;
import tech.devinhouse.veiculos.dto.MultaResponse;
import tech.devinhouse.veiculos.dto.VeiculoRequest;
import tech.devinhouse.veiculos.dto.VeiculoResponse;
import tech.devinhouse.veiculos.models.Multa;
import tech.devinhouse.veiculos.models.TipoVeiculo;
import tech.devinhouse.veiculos.models.Veiculo;
import tech.devinhouse.veiculos.service.VeiculoService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

  @Autowired
  private VeiculoService veiculoService;

  @Autowired
  private ModelMapper mapper;

  @GetMapping
  public ResponseEntity<List<VeiculoResponse>> consultar(){
    var veiculos = veiculoService.findAll();
    var resp = new ArrayList<VeiculoResponse>();
    for(Veiculo veiculo : veiculos){
      var veicDTO = mapper.map(veiculo, VeiculoResponse.class);
      if (veiculo.temMultas()){
        var multasDTO = veiculo.getMultas().stream().map(multa -> mapper.map(multa, MultaResponse.class)).toList();
        veicDTO.setMultas(multasDTO);
      }
      resp.add(veicDTO);
    }
    return ResponseEntity.ok(resp);
  }

  @GetMapping("/{placa}")
  public ResponseEntity<VeiculoResponse> consultar(@PathVariable ("placa") String placa){
    Veiculo veiculo = veiculoService.findByPlaca(placa);
    var resp = mapper.map(veiculo, VeiculoResponse.class);
    if (veiculo.temMultas()) {
      var multasDTO = veiculo.getMultas().stream().map(multa -> mapper.map(multa, MultaResponse.class)).toList();
      resp.setMultas(multasDTO);
      return ResponseEntity.ok(resp);
    } else {
      return ResponseEntity.ok(resp);
    }
  }

  @PostMapping
  public ResponseEntity<VeiculoResponse> cadastrarVeiculo(@RequestBody @Valid VeiculoRequest request) {
    var veiculo = mapper.map(request, Veiculo.class);
    veiculo = veiculoService.save(veiculo);
    var resp = mapper.map(veiculo, VeiculoResponse.class);
    return ResponseEntity.created(URI.create(veiculo.getPlaca())).body(resp);
  }

  @PostMapping("/{placa}/multas")
  public ResponseEntity<MultaResponse> cadastrarMulta(@PathVariable("placa") String placa,@RequestBody @Valid MultaRequest request) {
    var multa = mapper.map(request, Multa.class);
    multa = veiculoService.createMulta(placa, multa);
    var resp = mapper.map(multa, MultaResponse.class);
    return ResponseEntity.ok(resp);
  }

  @PostMapping("/dados")
  public ResponseEntity<?> carregarDados() {
    var veiculos = veiculoService.findAll();
    if (veiculos.isEmpty()) {
      Veiculo veiculo1 = new Veiculo("ABC-1234", TipoVeiculo.AUTOMOVEL, "Bat-Movel", 2022, "preto");
      Veiculo veiculo2 = new Veiculo("BCA-4321", TipoVeiculo.ONIBUS, "Enterprise", 1960, "prata");
      veiculoService.save(veiculo1);
      veiculoService.save(veiculo2);
      Multa multa1Veic1 = new Multa("Farol apagado", "Gothan City", 250F, veiculo1);
      Multa multa2Veic1 = new Multa("Insulfilm", "Gothan City", 100F, veiculo1);
      Multa multa1Veic2 = new Multa("Excesso velocidade", "Hiper-espaço", 400F, veiculo2);
      veiculoService.createMulta(veiculo1.getPlaca(),multa1Veic1);
      veiculoService.createMulta(veiculo1.getPlaca(),multa2Veic1);
      veiculoService.createMulta(veiculo2.getPlaca(),multa1Veic2);
    }

    return ResponseEntity.ok().build();
  }



}
