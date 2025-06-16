package br.com.cotiinformatica.exceptions;

@SuppressWarnings("serial")
public class EmailJaCadastradoException extends RuntimeException {

	//Atributo para armazenar o valor do email inválido.
	private String email;
	
	//Método construtor para receber o email inválido
	public EmailJaCadastradoException(String email) {
		this.email = email;
	}
	
	/*
	 * Método para retornar a mensagem de erro
	 */
	@Override
	public String getMessage() {		
		return "O email " + email + " já está cadastrado.";
	}
}