package com.skc.url.shorten.utils;

public class CommonConstraints {
	public static final String ENCRYPTION_SALT="sitakant";
	public static final String ENCRYPTION_ALGORITHM="AES";
	public static final String ENCRYPTION_INSTANCE_TYPE=ENCRYPTION_ALGORITHM+"/ECB/PKCS5Padding";
	
	
	/**
	 * Mongo Constraints
	 * */
	
	public static final String DEFAULT_HOST="localhost";
	public static final Integer DEFAULT_PORT=27017;
	public static final String DATABASE_NAME="UrlShorten";
	public static final String DB_COLLECTIONS="url_data";
	
	/**
	 * Data Constraints
	 * */
	public static final String REDIRECTED_NUMBER = "redirect_number";
	public static final String VERSION_V1 = "v1";
	public static final String VERSION = "version";
	public static final String CREATED_DATE = "created_date";
	public static final String SHORT_LINK = "shortLink";
	public static final String REQUEST_URL = "request_url";
	public static final String HOST_URL = "http://localhost:8080";
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String SHORT_URL = "short_url";
	public static final String UPDATED_DATE = "updated_date";
	public static final String SHORTEN_PATH = "shortenPath";
	/**
	 * Delimeter Details
	 * */
	public static final String DELIM_SLASH = "/";
	public static final String DELIM_BLANK = "";
	public static final String DELIM_COLLON = ":";
	public static final String DELIM_SPACE = " ";
	public static final String DELIM_AT = "@";
	/**
	 * Error Code
	 * */
	public static final  String ERROR_DB_400="DB_400";
	public static final String ERROR_DB_500="DB_500";
	public static final String ERROR_WEB_500="WEB_500";
	public static final String ERROR_WEB_400="WEB_400";
	public static final String ERROR_WEB_300="WEB_300";
	/**
	 * Error Message
	 * */
	public static final String ERROR_DB_400_MSG="Unable to reach database. Please try After Some time";
	public static final String ERROR_DB_500_MSG="Required Data Not found in Database.";
	public static final String ERROR_WEB_500_MSG="Some Error Occurs. Please try after some time";
	public static final String ERROR_WEB_400_MSG="Unable to locate the requested service . Please Try after some time";
	public static final String ERROR_WEB_300_MSG="Redirect Web Request.";
	/**
	 * User Related Constraints
	 * */
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String LASTNAME = "lastname";
	public static final String FIRSTNAME = "firstname";
	public static final String PATH_USER = "/user";
}
