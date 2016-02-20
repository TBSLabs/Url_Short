package com.skc.url.shorten.exception;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="error")
public class ErrorMessage {
	private String errorCode;
	private String errorMessage;
	private String errorLink;
	
	public ErrorMessage(){
		
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	@XmlElement(name="errorCode")
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	@XmlElement(name="message")
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorLink() {
		return errorLink;
	}
	@XmlElement(name="link")
	public void setErrorLink(String errorLink) {
		this.errorLink = errorLink;
	}
	
	
}
