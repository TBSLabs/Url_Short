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
}
