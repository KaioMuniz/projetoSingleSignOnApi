package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;
import br.com.cotiinformatica.interfaces.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

	@Autowired UsuarioService usuarioService;
	
	@PostMapping("criar")
	public ResponseEntity<CriarUsuarioResponse> criar(@RequestBody CriarUsuarioRequest request) {
		return ResponseEntity.ok(usuarioService.criarUsuario(request)); 
	}
	
	@PostMapping("autenticar")
	public ResponseEntity<AutenticarUsuarioResponse> autenticar(@RequestBody AutenticarUsuarioRequest request) {
		return ResponseEntity.ok(usuarioService.autenticarUsuario(request));
	}
}
