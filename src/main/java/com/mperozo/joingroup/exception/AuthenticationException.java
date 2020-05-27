package com.mperozo.joingroup.exception;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 6274768322328919259L;

	public AuthenticationException(String mensagem) {
		super(mensagem);
	}
}
