package com.mperozo.joingroup.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3240510879241605718L;

	public BusinessException(String mensagem) {
		super(mensagem);
	}
}
