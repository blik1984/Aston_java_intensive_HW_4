package com.homework.four.service;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ServiceException (String message) {
		super(message);
	}
	public ServiceException (Exception e) {
		super(e);
	}
	public ServiceException (String message, Exception e) {
		super(message, e);
	}

}
