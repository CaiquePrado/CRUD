package br.com.crud.domain._exceptions;

public class CpfAlreadyExistsException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CpfAlreadyExistsException(String exception) {
		super(exception);
	}
}
