package br.com.cotiinformatica.entities;

import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.enums.Method;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Permissao {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID id;
	
	@Column(length = 50, nullable = false, unique = true)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private Method method;
	
	@Column(length = 50, nullable = false)
	private String endpoint;
	
	@ManyToMany(mappedBy = "permissoes")
	private List<Perfil> perfis;
	
	
}
