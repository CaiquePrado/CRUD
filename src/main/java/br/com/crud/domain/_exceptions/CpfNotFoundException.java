package br.com.crud.domain._exceptions;

public class CpfNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CpfNotFoundException(String exception) {
		super(exception);
	}
}
