package com.skc.url.shorten.exception;

public class SystemGenericException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;
	private String errorLink;
	
	public SystemGenericException(){
		super();
	}
	
	public SystemGenericException(String errorCode, String errorMessage,
			String errorLink) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorLink = errorLink;
	}
	public SystemGenericException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorLink() {
		return errorLink;
	}
	public void setErrorLink(String errorLink) {
		this.errorLink = errorLink;
	}
	
	

}
