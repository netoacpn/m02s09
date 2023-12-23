package tech.devinhouse.veiculos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.devinhouse.veiculos.exception.RegistroJaExistenteException;
import tech.devinhouse.veiculos.models.Usuario;
import tech.devinhouse.veiculos.repository.UsuarioRepository;

import java.util.*;

@Service
public class UsuarioService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  public Usuario inserir(Usuario usuario) {
    if (usuarioRepository.existsByEmail(usuario.getEmail()))
      throw new RegistroJaExistenteException("Usuario", usuario.getEmail());
    String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    return usuarioRepository.save(usuario);
  }

  public List<Usuario> consultar() {
    return usuarioRepository.findAll();
  }

  @Override
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
    if (usuarioOpt.isEmpty())
      throw new UsernameNotFoundException("User not found");
    return usuarioOpt.get();
  }
}
