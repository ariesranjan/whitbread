Feature: Create Customer
	
	@postive
	Scenario: Verify that customer is created successfully with status code 200
		Given base uri sets for Create Customer api
		When a customer created using post method of hotel API
		Then the status code is 200
		Then validates create customer API response schema
		Given base uri sets for Retrieve Email Address api
		When a email address is retrieved using hotel API
		Then the status code is 200
		Then validates retrieve email address API response schema
		When customer id is deleted using email address
		Then the status code is 200
		