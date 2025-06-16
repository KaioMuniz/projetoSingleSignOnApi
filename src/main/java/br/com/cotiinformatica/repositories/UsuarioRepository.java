package br.com.cotiinformatica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	@Query("""
			SELECT COUNT(u) > 0 
			FROM Usuario u
			WHERE u.email = :email
			""")
	boolean existsByEmail(
			@Param("email") String email
			);
	
	@Query("""
			SELECT u FROM Usuario u
			JOIN FETCH u.perfil
			WHERE u.email = :email
			  AND u.senha = :senha
			""")
	Usuario find(
			@Param("email") String email,
			@Param("senha") String senha
			);
}
