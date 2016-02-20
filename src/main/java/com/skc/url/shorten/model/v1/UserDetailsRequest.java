package com.skc.url.shorten.model.v1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <p>JAX-B Class for User related payload</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@XmlRootElement(name="request")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsRequest {
	private String userEmail;
	private String firstName;
	private String lastName;
	private String updatedPassword;
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
	public String getUpdatedPassword() {
		return updatedPassword;
	}
	@XmlElement(name="updatedPassword",nillable=true,required=false)
	public void setUpdatedPassword(String updatedPassword) {
		this.updatedPassword = updatedPassword;
	}
}
