package com.skc.url.shorten.model.v1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name="response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlModelResponse {
	private String urlGiven;
	private String shortenUrl;
	private String statusMessage;
	private String statusCode;
	
	public String getUrlGiven() {
		return urlGiven;
	}
	@XmlElement(name="requestedUrl")
	public void setUrlGiven(String urlGiven) {
		this.urlGiven = urlGiven;
	}
	public String getShortenUrl() {
		return shortenUrl;
	}
	@XmlElement(name="responseUrl")
	public void setShortenUrl(String shortenUrl) {
		this.shortenUrl = shortenUrl;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	@XmlElement(nillable=true,name="message")
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getStatusCode() {
		return statusCode;
	}
	@XmlElement(name="status",nillable=false,required=true)
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
