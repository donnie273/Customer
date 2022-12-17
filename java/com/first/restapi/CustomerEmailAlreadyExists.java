package com.first.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerEmailAlreadyExists extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomerEmailAlreadyExists(String message) {
		super(message);
	}
	
}
