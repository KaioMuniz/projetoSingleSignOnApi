package br.com.cotiinformatica.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.repositories.PerfilRepository;

@Service
public class DataLoader implements ApplicationRunner {

	
	@Autowired PerfilRepository perfilRepository;
	
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub

	}
}
