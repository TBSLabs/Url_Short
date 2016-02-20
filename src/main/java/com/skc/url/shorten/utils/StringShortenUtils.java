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
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;

import com.skc.url.shorten.exception.SystemGenericException;

/**
 * <p> This class will have all the Details for the String Shortening Logic </p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */

public class StringShortenUtils {	
	static final Logger LOG = Logger.getLogger(StringUtils.class);

	public static String getShortenURL(String url,String userName)  {
		byte[] key = createKey(userName).substring(0, 16).getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(key, CommonConstraints.ENCRYPTION_ALGORITHM);
		Cipher c;
		try {
			c = Cipher.getInstance(CommonConstraints.ENCRYPTION_INSTANCE_TYPE);
			return encryptionName(url,c,secretKey).substring(0, 12).replace(CommonConstraints.DELIM_SLASH, CommonConstraints.DELIM_AT);
		} catch (InvalidKeyException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (NoSuchPaddingException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (IllegalBlockSizeException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (BadPaddingException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		}
	}
	
	/**
	 * <p> This method will use for password encryption.</p>
	 * @param name type of {@link String}
	 * @return {@link String}
	 * @throws InvalidKeyException,{@link NoSuchAlgorithmException}
	 * */
	public static String getEncryptedPassword(String name) {
		byte[] key = (CommonConstraints.ENCRYPTION_TYPE+CommonConstraints.ENCRYPTION_SALT).getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(key, CommonConstraints.ENCRYPTION_ALGORITHM);
		Cipher c;
		try {
			c = Cipher.getInstance(CommonConstraints.ENCRYPTION_INSTANCE_TYPE);
			return encryptionName(name,c,secretKey);
		} catch (InvalidKeyException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (NoSuchPaddingException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (IllegalBlockSizeException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (BadPaddingException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		}
	}
	
	/**
	 * <p> This method will use for password decryption.</p>
	 * @param name type of {@link String}
	 * @return {@link String}
	 * @throws InvalidKeyException,{@link NoSuchAlgorithmException}
	 * */
	public static String decryptPassword(String name) {
		byte[] key = (CommonConstraints.ENCRYPTION_TYPE+CommonConstraints.ENCRYPTION_SALT).getBytes();
		final SecretKeySpec secretKey = new SecretKeySpec(key, CommonConstraints.ENCRYPTION_ALGORITHM);
		byte[] encyString=null;
		Cipher c;
		try {
			c = Cipher.getInstance(CommonConstraints.ENCRYPTION_INSTANCE_TYPE);
			c.init(Cipher.DECRYPT_MODE, secretKey);
			encyString = c.doFinal(Base64.decodeBase64(name));
		} catch (InvalidKeyException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (NoSuchPaddingException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (IllegalBlockSizeException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		} catch (BadPaddingException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		}
		if(ObjectUtils.checkNull(encyString)){
			throw new SystemGenericException(CommonConstraints.ERROR_VALIDATE_2000,CommonConstraints.ERROR_VALIDATE_2000_MSG);
		}
		return new String(encyString);
	}

	private static String createKey(String userName) {
		String returnString = userName.substring(0,3).concat(new Date().getTime()+CommonConstraints.DELIM_BLANK).replace(CommonConstraints.DELIM_SPACE, CommonConstraints.DELIM_BLANK).replace(CommonConstraints.DELIM_COLLON, CommonConstraints.DELIM_BLANK);
		return returnString;
	}
	
	private static String encryptionName(String name,Cipher c,SecretKeySpec secretKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		c.init(Cipher.ENCRYPT_MODE, secretKey);
		String encyString = Base64.encodeBase64String(c.doFinal(name.getBytes()));
		return encyString;
	}
}
