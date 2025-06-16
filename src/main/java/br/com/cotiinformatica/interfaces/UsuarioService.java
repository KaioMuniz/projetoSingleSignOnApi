package br.com.cotiinformatica.interfaces;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;

public interface UsuarioService {

	CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request);
	
	AutenticarUsuarioResponse autenticarUsuario(AutenticarUsuarioRequest request);
}
