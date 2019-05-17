package br.com.airton.exceptions;

import javax.servlet.ServletException;

public class TokenExpired extends ServletException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenExpired(String message){
        super(message);
    }
}
