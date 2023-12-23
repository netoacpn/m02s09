package tech.devinhouse.veiculos.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.veiculos.dto.AutenticacaoRequest;
import tech.devinhouse.veiculos.dto.AutenticacaoResponse;
import tech.devinhouse.veiculos.dto.UsuarioRequest;
import tech.devinhouse.veiculos.dto.UsuarioResponse;
import tech.devinhouse.veiculos.models.Usuario;
import tech.devinhouse.veiculos.service.AutenticacaoService;
import tech.devinhouse.veiculos.service.UsuarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> consultar() {
        var usuarios = usuarioService.consultar();
        var resp = usuarios.stream().map(u -> mapper.map(u, UsuarioResponse.class)).toList();
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> inserir(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var usuario = mapper.map(usuarioRequest, Usuario.class);
        usuario = usuarioService.inserir(usuario);
        var resp = mapper.map(usuario, UsuarioResponse.class);
        return ResponseEntity.created(URI.create(usuario.getId().toString())).body(resp);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<AutenticacaoResponse> login(@RequestBody @Valid AutenticacaoRequest request) {
        var token = autenticacaoService.autenticar(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(new AutenticacaoResponse(token));
    }

}
