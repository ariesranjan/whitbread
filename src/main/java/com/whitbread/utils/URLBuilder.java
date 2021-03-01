package com.whitbread.utils;

import com.whitbread.dataProviders.ConfigFileReader;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


public class URLBuilder {
	
	ConfigFileReader config = new ConfigFileReader();
	
	public RequestSpecification getBaseURI(String endPoint){
		RestAssured.baseURI = config.getConfig("BaseUrl")+endPoint;
		RequestSpecification request = RestAssured.given();
		return request;
	}

}
