package com.skc.url.shorten.utils;

public class CommonConstraints {
	
	/**
	 * Authentication and Authorization
	 * */
	public static final String ENCRYPTION_ALGORITHM="AES";
	public static final String ENCRYPTION_SALT="sitakant";
	public static final String ENCRYPTION_TYPE=ENCRYPTION_ALGORITHM+" Algo";
	public static final String ENCRYPTION_INSTANCE_TYPE=ENCRYPTION_ALGORITHM+"/ECB/PKCS5Padding";
	public static final String AUTHENTICATION_HEADER = "Authorization";
	public static final String SHA_HASH_PROGRAMME="SHA1PRNG";
	
	/**
	 * Mongo Constraints
	 * */
	
	public static final String DEFAULT_HOST="localhost";
	public static final Integer DEFAULT_PORT=27017;
	public static final String DATABASE_NAME="UrlShorten";
	public static final String DB_COLLECTIONS_URL="url_data";
	public static final String DB_COLLECTION_USER = "user_collection";
	
	/**
	 * Data Constraints
	 * */
	public static final String REDIRECTED_NUMBER = "redirect_number";
	public static final String VERSION_V1 = "v1";
	public static final String VERSION = "version";
	public static final String CREATED_DATE = "created_date";
	public static final String SHORT_LINK = "shortLink";
	public static final String REQUEST_URL = "request_url";
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String SHORT_URL = "short_url";
	public static final String UPDATED_DATE = "updated_date";
	public static final String SHORTEN_PATH = "shortenPath";
	
	/**
	 * Mongo Operator
	 * */
	public static final String MONGO_OR="or";
	/**
	 * Delimeter Details
	 * */
	public static final String DELIM_SLASH = "/";
	public static final String DELIM_BLANK = "";
	public static final String DELIM_COLLON = ":";
	public static final String DELIM_SPACE = " ";
	public static final String DELIM_AT = "@";
	public static final String DELIM_DOLLER="$";
	/**
	 * Error Code
	 * */
	public static final String ERROR_APP_400="APP_400";
	public static final String ERROR_DB_400="DB_400";
	public static final String ERROR_DB_500="DB_500";
	public static final String ERROR_WEB_500="WEB_500";
	public static final String ERROR_WEB_400="WEB_400";
	public static final String ERROR_WEB_300="WEB_300";
	public static final String ERROR_PROGRAMME_1000="PROG_1000";
	public static final String ERROR_VALIDATE_2000="VAL_2000";
	public static final String ERROR_DUPLICATE_3000="DUP_3000";
	
	/**
	 * Error Message
	 * */
	public static final String PLEASE_TRY_AFTER_SOME_TIME="Please try after some time.";
	public static final String ERROR_APP_400_MSG="Some Application needs to be initialize."+PLEASE_TRY_AFTER_SOME_TIME;
	public static final String ERROR_DB_400_MSG="Database is down."+PLEASE_TRY_AFTER_SOME_TIME;
	public static final String ERROR_DB_500_MSG="Required Data Not found in Database.";
	public static final String ERROR_WEB_500_MSG="Some Error Occurs."+PLEASE_TRY_AFTER_SOME_TIME;
	public static final String ERROR_WEB_400_MSG="Unable to locate the requested service ."+PLEASE_TRY_AFTER_SOME_TIME;
	public static final String ERROR_WEB_300_MSG="Redirect Web Request.";
	public static final String ERROR_PROGRAMME_1000_MSG="Unable to process request."+PLEASE_TRY_AFTER_SOME_TIME;
	public static final String ERROR_DUPLICATE_3000_MSG="Duplicate Record Exist. "+PLEASE_TRY_AFTER_SOME_TIME;
	public static final String ERROR_VALIDATE_2000_MSG="Unable to validate the date"+PLEASE_TRY_AFTER_SOME_TIME;
	/**
	 * User Related Constraints
	 * */
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String LASTNAME = "lastname";
	public static final String FIRSTNAME = "firstname";
	public static final String PATH_USER = "/user";
	public static final String IS_PASSWORD_CHANGE="is"+PASSWORD+"Change";
	/**
	 * Utility Constraints
	 * */
	public static final String DATE_YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd hh:mm:ss";
	public static final String DATE_YYYY_MM_DD_HH_MM_SS_S_Z="yyyy-MM-dd hh:mm:ss:SS:Z";
}
