package com.texoit.undertow.standalone.api;

public class DrowningException extends Exception {

	private static final long serialVersionUID = 2747869622010963495L;

	public DrowningException( Throwable cause ) {
		super( cause );
	}

	public DrowningException( String message, Throwable cause ) {
		super( message, cause );
	}

	public DrowningException( String message ) {
		super( message );
	}
}
