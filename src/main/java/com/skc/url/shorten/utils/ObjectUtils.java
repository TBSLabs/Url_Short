package com.skc.url.shorten.utils;

/**
 * <p>Object Related Utility Class</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
public class ObjectUtils {
	
	public static boolean checkNull(Object object){
		return object==null?true:false;
	}
	
	public static boolean checkNotNull(Object obj){
		return !checkNull(obj);
	}
	
	public static boolean checkEqualsString(String first,String secound){
		return first.equalsIgnoreCase(secound);
	}
}
