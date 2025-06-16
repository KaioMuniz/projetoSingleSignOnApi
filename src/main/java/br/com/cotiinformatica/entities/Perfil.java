package br.com.cotiinformatica.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column	
	private UUID id;
	
	@Column(length = 100, nullable = false, unique = true)
	private String nome;
	
	@OneToMany(mappedBy = "perfil")
	private List<Usuario> usuario;
	
	@ManyToMany
	@JoinTable(name = "perfil_permissao",
			joinColumns = @jakarta.persistence.JoinColumn(name = "perfil_id"), 
			inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "permissao_id"))
	private List<Permissao> permissoes;
}
