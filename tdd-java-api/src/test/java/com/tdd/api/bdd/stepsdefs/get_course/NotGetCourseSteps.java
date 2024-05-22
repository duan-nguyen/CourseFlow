package com.tdd.api.bdd.stepsdefs.get_course;

import static org.junit.Assert.assertEquals;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//@ContextConfiguration(classes = CucumberSpringContextConfiguration.class)
// @SpringBootTest
public class NotGetCourseSteps {

	private RestTemplate rest = new RestTemplate();
	private Integer code = null;
	private Integer port = 3001;
	private ObjectMapper mapper = new ObjectMapper();
	private HttpHeaders errorHeaders = null;
	private String responseStringBody = null;
	// private URI uri = null;

	// Create and add the course
	@Given("there is no course")
	public void there_is_no_course() {

	}

	@When("a user makes a GET request to non existing course {string}")
	public void a_user_makes_a_get_request_to_non_existing_course(String nonExistingResourceEndpoint) {
		try{
			rest.getForEntity("http://localhost:" + port + nonExistingResourceEndpoint, JsonNode.class);
		}catch(HttpClientErrorException exp){
			code = exp.getStatusCode().value();
			errorHeaders = exp.getResponseHeaders();
			responseStringBody = exp.getResponseBodyAsString();
		}
	}

	@Then("the response status code for non existing course should be {int}")
	public void the_response_status_code_for_non_existing_course_should_be(int statusCode) {
		assertEquals(statusCode, code.intValue());
	}

	@Then("the content type shoould be {string}")
	public void the_content_type_should_be(String expectedContentType) {
		assertEquals(expectedContentType, errorHeaders.getContentType().toString());
	}

	@Then("the response content for non existing course :")
	public void the_response_content_for_non_existing_course(String expectedContent) {
		JsonNode expectedJsonNode = null;
		JsonNode actualJsonNode = null;
		try {
			expectedJsonNode = mapper.readTree(expectedContent);
			actualJsonNode = mapper.readTree(responseStringBody);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		// Comparar ambos JSONs
		assertEquals(expectedJsonNode, actualJsonNode);
	}

}