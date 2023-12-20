package tech.devinhouse.veiculos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.veiculos.exception.RegistroJaExistenteException;
import tech.devinhouse.veiculos.exception.RegistroNaoEncontradoException;
import tech.devinhouse.veiculos.models.Multa;
import tech.devinhouse.veiculos.models.Veiculo;
import tech.devinhouse.veiculos.repository.MultaRepository;
import tech.devinhouse.veiculos.repository.VeiculoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

  @Autowired
  private VeiculoRepository veiculoRepository;

  @Autowired
  private MultaRepository multaRepository;

  @Autowired
  private ModelMapper mapper;


  public List<Veiculo> findAll() {
    return veiculoRepository.findAll();
  }

  public Veiculo findByPlaca(String placa) {
    return veiculoRepository.findById(placa).orElseThrow(()-> new RegistroNaoEncontradoException("Veiculo", placa));
  }

  public Veiculo save(Veiculo veiculo) {
    boolean existe = veiculoRepository.existsById(veiculo.getPlaca());
    if (existe)
      throw new RegistroJaExistenteException("Veiculo", veiculo.getPlaca());
    veiculo = veiculoRepository.save(veiculo);
    return veiculo;
  }

  public Multa createMulta(String placa, Multa multa) {
    var veiculo = this.findByPlaca(placa);
    multa.setVeiculo(veiculo);
    multa = multaRepository.save(multa);
    return multa;
  }
}
