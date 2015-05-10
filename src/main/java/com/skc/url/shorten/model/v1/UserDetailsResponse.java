package com.skc.url.shorten.model.v1;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skc.url.shorten.utils.CommonConstraints;

/**
 * <p> JAX-B Class for User Response <p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@XmlRootElement(name="response")
@XmlType(propOrder={"username","password","createdDate","home_url","message","links"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsResponse {
	private String username;
	private String password;
	private String message;
	private String createdDate;
	private String home_url;
	private List<UserLinks> links;
	public String getUsername() {
		return username;
	}
	@XmlElement(name=CommonConstraints.USERNAME)
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	@XmlElement(name=CommonConstraints.PASSWORD)
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	@XmlElement(name="message")
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	@XmlElement(name=CommonConstraints.CREATED_DATE)
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getHome_url() {
		return home_url;
	}
	@XmlElement(name="home_url")
	public void setHome_url(String home_url) {
		this.home_url = home_url;
	}
	@XmlElementWrapper
	@XmlElement(name="link",required=false,nillable=true,type=UserLinks.class)
	public List<UserLinks> getLinks() {
		return links;
	}
	public void setLinks(List<UserLinks> links) {
		this.links = links;
	}
	@XmlRootElement(name = "links")
	public static class UserLinks{
		private String requestedLinks;
		private String shortLink;
		private String redirectNumber;
		private String version;
		public String getRequestedLinks() {
			return requestedLinks;
		}
		@XmlElement(name=CommonConstraints.REQUEST_URL)
		public void setRequestedLinks(String requestedLinks) {
			this.requestedLinks = requestedLinks;
		}
		public String getShortLink() {
			return shortLink;
		}
		@XmlElement(name=CommonConstraints.SHORT_URL)
		public void setShortLink(String shortLink) {
			this.shortLink = shortLink;
		}
		public String getRedirectNumber() {
			return redirectNumber;
		}
		@XmlElement(name=CommonConstraints.REDIRECTED_NUMBER)
		public void setRedirectNumber(String redirectNumber) {
			this.redirectNumber = redirectNumber;
		}
		public String getVersion() {
			return version;
		}
		@XmlElement(name=CommonConstraints.VERSION)
		public void setVersion(String version) {
			this.version = version;
		}
		
		
	}
	
}
