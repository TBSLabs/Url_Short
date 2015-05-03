package com.skc.url.shorten.model.v1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>JAX-B Class for User related payload</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@XmlRootElement(name="request")
public class UserDetailsRequest {
	private String userEmail;
	private String firstName;
	private String lastName;
	private String password;
	//TODO Username shoud be remove. Automatic username as a number should generate
	private String username;
	public String getUserEmail() {
		return userEmail;
	}
	@XmlElement(name="email")
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getFirstName() {
		return firstName;
	}
	@XmlElement(name="firstname")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	@XmlElement(name="lastname")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	@XmlElement(name="password")
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	@XmlElement(name="username")
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
