package com.skc.url.shorten.secure;

import com.skc.url.shorten.exception.SystemGenericException;

/**
 * <p>This interface is having a method to create a random secure number</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
public interface SecureNumberGeneratorService {
	Integer getSecureId()throws SystemGenericException;
}
