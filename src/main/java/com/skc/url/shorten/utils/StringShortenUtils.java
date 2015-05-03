package com.skc.url.shorten.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * <p> This class will have all the Details for the String Shortening Logic </p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */

public class StringShortenUtils {	
	

	public static String getShortenURL(String url,String userName) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] key = createKey(userName).substring(0, 16).getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(key, CommonConstraints.ENCRYPTION_ALGORITHM);
		Cipher c = Cipher.getInstance(CommonConstraints.ENCRYPTION_INSTANCE_TYPE);
		return encryptionName(url,c,secretKey).substring(0, 12).replace(CommonConstraints.DELIM_SLASH, CommonConstraints.DELIM_AT);
	}

	private static String createKey(String userName) {
		String returnString = userName.substring(0,3).concat(new Date().getTime()+CommonConstraints.DELIM_BLANK).replace(CommonConstraints.DELIM_SPACE, CommonConstraints.DELIM_BLANK).replace(CommonConstraints.DELIM_COLLON, CommonConstraints.DELIM_BLANK);
		return returnString;
	}
	
	private static String encryptionName(String name,Cipher c,SecretKeySpec secretKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		c.init(Cipher.ENCRYPT_MODE, secretKey);
		String encyString = Base64.encodeBase64String(c.doFinal(name.getBytes()));
		return encyString;
	}
}
