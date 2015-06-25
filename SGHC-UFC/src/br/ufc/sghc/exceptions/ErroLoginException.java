package br.ufc.sghc.exceptions;

public class ErroLoginException extends RuntimeException{

	private String mensagem;
	
	public ErroLoginException(String mensagem){
		this.mensagem = mensagem;
	}

	@Override
	public String getMessage() {
		return this.mensagem;
	}
	
}
