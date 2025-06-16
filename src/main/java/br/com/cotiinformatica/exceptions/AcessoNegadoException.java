package br.com.cotiinformatica.exceptions;

@SuppressWarnings("serial")
public class AcessoNegadoException extends RuntimeException {


	@Override
	public String getMessage() {
		return "Acesso negado! Você não tem permissão para acessar este recurso.";
	}

}
