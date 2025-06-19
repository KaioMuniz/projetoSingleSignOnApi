package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequest;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponse;
import br.com.cotiinformatica.dtos.CriarUsuarioRequest;
import br.com.cotiinformatica.dtos.CriarUsuarioResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjetoSingleSignOnApiApplicationTests {

	@Autowired
	MockMvc mockMvc; // testes de integração na API REST
	@Autowired
	ObjectMapper mapper; // serializar e deserializar dados em JSON

	// Atributos para armazenar os dados do usuário criado no primeiro teste
	private static String emailUsuario;
	private static String senhaUsuario;

	@Test
	@Order(1)
	void deveCriarUmUsuarioComSucesso() {

		try {

			var faker = new Faker();

			// Arrange (criando os dados do teste)
			var request = new CriarUsuarioRequest();

			request.setNome(faker.name().fullName());
			request.setEmail(faker.internet().emailAddress());
			request.setSenha("@Teste2025");

			// Act (executar o endpoint POST /api/v1/usuario/criar)
			var result = mockMvc.perform(post("/api/v1/usuario/criar") // endereço do serviço da API
					.contentType("application/json") // enviando dados em formato JSON
					.content(mapper.writeValueAsString(request))) // serializando em json
					.andExpect(status().isOk()) // esperando que a resposta seja 200 (OK)
					.andReturn(); // capturando o conteudo da resposta

			// Assert (verificar o resultado obtido)
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			var response = mapper.readValue(content, CriarUsuarioResponse.class);

			assertNotNull(response.getId()); // verificando se o ID está preenchido
			assertEquals(response.getNome(), request.getNome()); // verificando o nome é igual ao cadastrado
			assertEquals(response.getEmail(), request.getEmail()); // verificando o email é igual ao cadastrado
			assertEquals(response.getPerfil(), "Operador"); // verificando se o perfil é operador
			assertNotNull(response.getDataHoraCriacao()); // verificando se a data está preenchida

			// guardar o nome e a senha do usuário
			emailUsuario = request.getEmail();
			senhaUsuario = request.getSenha();
		} catch (Exception e) {
			fail("Teste falhou: " + e.getMessage());
		}
	}

	@Test
	@Order(2)
	void deveRetornarErroQuandoEmailJaExiste() {

		try {

			var faker = new Faker();

			// Arrange (criando os dados do teste)
			var request = new CriarUsuarioRequest();

			request.setNome(faker.name().fullName());
			request.setEmail(emailUsuario);
			request.setSenha(senhaUsuario);

			// Act (executar o endpoint POST /api/v1/usuario/criar)
			var result = mockMvc.perform(post("/api/v1/usuario/criar") // endereço do serviço da API
					.contentType("application/json") // enviando dados em formato JSON
					.content(mapper.writeValueAsString(request))) // serializando em json
					.andExpect(status().isBadRequest()) // esperando que a resposta seja 400 (BAD REQUEST)
					.andReturn(); // capturando o conteudo da resposta

			// Assert (verificar o resultado obtido)
			var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

			// verificando se a resposta contem a frase abaixo
			assertTrue(response.contains("O email " + emailUsuario + " já está cadastrado."));
		} catch (Exception e) {
			fail("Teste falhou: " + e.getMessage());
		}
	}

	@Test
	@Order(3)
	void deveAutenticarUsuarioComSucesso() {

		try {

			// Arrange (criando os dados do teste)
			var request = new AutenticarUsuarioRequest();

			request.setEmail(emailUsuario);
			request.setSenha(senhaUsuario);

			// Act (executar o endpoint POST /api/v1/usuario/autenticar)
			var result = mockMvc.perform(post("/api/v1/usuario/autenticar") // endereço do serviço da API
					.contentType("application/json") // enviando dados em formato JSON
					.content(mapper.writeValueAsString(request))) // serializando em json
					.andExpect(status().isOk()) // esperando que a resposta seja 200 (OK)
					.andReturn(); // capturando o conteudo da resposta

			// Assert (verificar o resultado obtido)
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			var response = mapper.readValue(content, AutenticarUsuarioResponse.class);

			assertNotNull(response.getId()); // verificando se o ID está preenchido
			assertNotNull(response.getNome()); // verificando se o nome está preenchido
			assertEquals(response.getEmail(), request.getEmail()); // verificando o email é igual ao cadastrado
			assertEquals(response.getPerfil(), "Operador"); // verificando se o perfil é operador
			assertNotNull(response.getDataHoraAcesso()); // verificando se a data está preenchida
			assertNotNull(response.getDataHoraExpiracao()); // verificando se a data está preenchida
			assertNotNull(response.getAccessToken()); // verificando se o token está preenchido
		} catch (Exception e) {
			fail("Teste falhou: " + e.getMessage());
		}
	}

	@Test
	@Order(4)
	void deveRetornarAcessoNegadoQuandoUsuarioInvalido() {
		fail("Não implementado.");
	}

}
