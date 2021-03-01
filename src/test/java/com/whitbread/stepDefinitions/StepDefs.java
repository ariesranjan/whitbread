package com.whitbread.stepDefinitions;


import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import com.whitbread.dataProviders.ConfigFileReader;
import com.whitbread.utils.RandomDataGeneration;
import com.whitbread.utils.RequestBuilder;
import com.whitbread.utils.URLBuilder;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

	public class StepDefs {
		
		RequestBuilder reqBuilder = new RequestBuilder();
		RandomDataGeneration dataGenerator = new RandomDataGeneration();
		private Response response;
		private ValidatableResponse json;
		private RequestSpecification request;
		ConfigFileReader config = new ConfigFileReader();
		private URLBuilder urlBuilder = new URLBuilder();
	    protected String email;
	    
		@Given("base uri sets for (.*) api")
		public void base_uri_set(String service){
			switch(service) {
				case "Create Customer":
					request = urlBuilder.getBaseURI("/api/hotel/customer");
					request = request.contentType("application/json");
					break;
			}
		}

		@When("a customer created using post method of hotel API")
		public void a_customer_created_using_post_method(){
			JSONObject createRequest = reqBuilder.requestBuilder("createBitlinksRequest.json");
			email = dataGenerator.randomEmail();
			createRequest.put("email", email);
			createRequest.put("password", "123456789");
			createRequest.put("firstName", dataGenerator.randomFirstName());
			createRequest.put("lastName", dataGenerator.randomLastName());
			createRequest.put("lastName", dataGenerator.randomTitle());
			request = request.body(createRequest.toJSONString());
			response = request.post();
		}
		
		@When("a email address is retrieved using hotel API")
		public void a_email_address_is_retrieved_using_hotel_api(){
			request = urlBuilder.getBaseURI("/api/hotel/customer/"+email);
			response = request.when().get();
		}
		
		@When("customer id is deleted using email address")
		public void customer_id_is_deleted_using_email_address(){
			request = urlBuilder.getBaseURI("/api/hotel/customer/"+email);
			response = request.when().delete();
		}

		@Then("the status code is (\\d+)")
		public void verify_status_code(int statusCode){
			json = response.then().statusCode(statusCode);
		}
		
		@And("validates create customer API response schema$")
		public void validates_create_customer_API_response_schema(){
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("testData/createCustomerSchema.json"));
		}
		
		@And("validates retrieve email address API response schema$")
		public void validates_retrieve_email_address_API_response_schema(){
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("testData/retrieveGroupSchema.json"));
		}
		

		@And("response includes the following$")
		public void response_equals(Map<String,String> responseFields){
			for (Map.Entry<String, String> field : responseFields.entrySet()) {
				if(StringUtils.isNumeric(field.getValue())){
					json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
				}
				else{
					json.body(field.getKey(), equalTo(field.getValue()));
				}
			}
		}

}
