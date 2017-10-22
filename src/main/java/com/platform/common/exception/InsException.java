package com.platform.common.exception;

/**
 * 平台异常
 *
 */
public class InsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public InsException(){
		super("Platform Exception");
	}
	
	public InsException(String message){
		super(message);
	}
	
	public InsException(String message, Throwable cause) {
        super(message, cause);
    }

	@Override
	public String getMessage() {		
		return super.getMessage();
	}
	
}
