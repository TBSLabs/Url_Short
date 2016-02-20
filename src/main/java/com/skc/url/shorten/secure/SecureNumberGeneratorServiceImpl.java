package com.skc.url.shorten.secure;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p> This class will generate a Unique Secure Random Number usnig SHA Algorithm</p>
 * <p> References : <ul>
 * 					<li>http://stackoverflow.com/questions/27622625/securerandom-with-nativeprng-vs-sha1prng</li>
 * 					<li>http://www.coderanch.com/t/328111/java/java/Generate-Digit-Random-Number</li>
 * 					</ul>
 * </p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Service("secureNumberGeneratorService")
public class SecureNumberGeneratorServiceImpl implements SecureNumberGeneratorService{
	final static Logger LOG = Logger.getLogger(SecureNumberGeneratorServiceImpl.class);
	
	@Value("${securenumber.upto.digit}")
	Integer secureDigit;
	
	
	public Integer getSecureId() throws SystemGenericException {
		SecureRandom secureRandom=null;
		Integer secureId = 0;
		
		try {
			 secureRandom = SecureRandom.getInstance(CommonConstraints.SHA_HASH_PROGRAMME);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		}
		if(!ObjectUtils.checkNull(secureRandom)){
			secureId = Math.abs(secureRandom.nextInt()/secureDigit);
			if(((int)Math.log10(secureId)+1)<((int)Math.log10(secureDigit)+2)){
				secureId=getSecureId();
			}
		}
		return secureId;
	}
}
