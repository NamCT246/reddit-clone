package com.namct.reddit.exceptions;

public class BaseException extends RuntimeException{
	private static final long serialVersionUID = -5371427923641844461L;

	public BaseException(String exceptionMessage, Exception exception) {
        super(exceptionMessage, exception);
    }

    public BaseException(String exceptionMessage) {
        super(exceptionMessage);
    }
}