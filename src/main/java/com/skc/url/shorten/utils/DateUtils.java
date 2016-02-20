package com.skc.url.shorten.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.skc.url.shorten.exception.SystemGenericException;

/**
 * <p>Utility class for date related operations</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
public class DateUtils {
	final static Logger LOG = Logger.getLogger(DateUtils.class);
	
	public static String convertDate(String format,Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static final Date createDate(String format,String dateValue){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date  = dateFormat.parse(dateValue);
		} catch (ParseException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_PROGRAMME_1000,CommonConstraints.ERROR_PROGRAMME_1000_MSG);
		}
		return date;
	}
}
