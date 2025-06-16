package br.com.cotiinformatica.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.entities.Perfil;
import br.com.cotiinformatica.repositories.PerfilRepository;

@Service
public class DataLoader implements ApplicationRunner {

	@Autowired PerfilRepository perfilRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		//criando o perfil 'Administrador' no banco caso não exista
		if( ! perfilRepository.existsByNome("Administrador")) {
			
			var perfil = new Perfil();
			perfil.setNome("Administrador");
			
			perfilRepository.save(perfil);
		}
		
		//criando o perfil 'Operador' no banco caso não exista
		if( ! perfilRepository.existsByNome("Operador")) {
			
			var perfil = new Perfil();
			perfil.setNome("Operador");
			
			perfilRepository.save(perfil);
		}	
	}
}
