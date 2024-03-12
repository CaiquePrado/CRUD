package br.com.crud.domain._exceptions;

public class CpfMismatchException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CpfMismatchException(String exception) {
		super(exception);
	}
}
