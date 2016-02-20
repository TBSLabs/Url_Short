package com.skc.url.shorten.auth.service;
/**
 * <p> Authenticate Service. Currently, this will support the Basic Authentication</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
public interface AuthenticateService {
	public boolean authenticate(String credential);
	public boolean checkForShortUrl(String url);
}
