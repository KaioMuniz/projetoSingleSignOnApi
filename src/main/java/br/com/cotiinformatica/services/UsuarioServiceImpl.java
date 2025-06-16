package br.com.cotiinformatica.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.CryptoComponent;
import br.com.cotiinformatica.components.JwtTokenComponent;
import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.AcessoNegadoException;
import br.com.cotiinformatica.exceptions.EmailJaCadastradoException;
import br.com.cotiinformatica.interfaces.UsuarioService;
import br.com.cotiinformatica.repositories.PerfilRepository;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired UsuarioRepository usuarioRepository;
	@Autowired PerfilRepository perfilRepository;
	
	@Autowired CryptoComponent cryptoComponent;
	@Autowired JwtTokenComponent jwtTokenComponent;
	
	@Override
	public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) {

		//Verificar se o email informado já existe no banco de dados
		if(usuarioRepository.existsByEmail(request.getEmail()))
			throw new EmailJaCadastradoException(request.getEmail());
		
		var usuario = new Usuario(); //criando um objeto da entidade
		
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(cryptoComponent.getSHA256(request.getSenha()));
		usuario.setPerfil(perfilRepository.findByNome("Operador"));
		
		usuarioRepository.save(usuario); //gravar o usuário no banco de dados
		
		var response = new CriarUsuarioResponse(); //dados da resposta
		
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setDataHoraCriacao(LocalDateTime.now());
		response.setPerfil(usuario.getPerfil().getNome());
		
		return response;
	}

	@Override
	public AutenticarUsuarioResponse autenticarUsuario(AutenticarUsuarioRequest request) {

		//pesquisar o usuário no banco de dados através do email e da senha
		var usuario = usuarioRepository.find(request.getEmail(), cryptoComponent.getSHA256(request.getSenha()));
		
		//verificar se o usuário não foi encontrado
		if(usuario == null)
			throw new AcessoNegadoException();
		
		var response = new AutenticarUsuarioResponse(); //dados da resposta
		
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setDataHoraAcesso(LocalDateTime.now());
		response.setPerfil(usuario.getPerfil().getNome());
		
		//gerando o TOKEN JWT
		response.setAccessToken(jwtTokenComponent.generateToken(usuario.getEmail(), usuario.getPerfil().getNome()));
		
		//gerando a data e hora de expiração do TOKEN
		response.setDataHoraExpiracao(jwtTokenComponent.getExpirationDate().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDateTime());
		
		return response;
	}

}
