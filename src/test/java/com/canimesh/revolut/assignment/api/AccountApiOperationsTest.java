package com.canimesh.revolut.assignment.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.canimesh.revolut.assignment.MoneyTransferApplication;
import com.canimesh.revolut.assignment.account.rest.dto.CreateAccountRequest;

import io.restassured.response.Response;

public class AccountApiOperationsTest extends BaseApiTest {

	
	@Test
	public void test_return_400_if_owner_empty() {
		given().baseUri(HTTP_LOCALHOST).and().when().body(new CreateAccountRequest(10, "")).post("accounts")
				.then().assertThat().statusCode(400);
	}

	@Test
	public void test_return_400_if_amount_less_than_0() {
		given().baseUri(HTTP_LOCALHOST).and().when().body(new CreateAccountRequest(-5, "")).post("accounts")
				.then().assertThat().statusCode(400);
	}

	@Test
	public void test_return_404_not_found() {
		given().baseUri(HTTP_LOCALHOST).and().when().get("/accounts/wrong_id").then().assertThat()
				.statusCode(404);
	}

	@Test
	public void test_return_201_successful_created() {
		given().baseUri(HTTP_LOCALHOST).and().when().body(new CreateAccountRequest(100, "Animesh"))
				.post("accounts").then().assertThat().statusCode(201).header("location", notNullValue());
	}

	@Test
	public void test_return_201_successful_created_and_fetch() {
		Response response = given().baseUri(HTTP_LOCALHOST).and().when()
				.body(new CreateAccountRequest(100, "Animesh")).post("accounts").andReturn();
		response.then().assertThat().statusCode(201).header("location", notNullValue());
		String newAccountLocation = response.getHeader("location");
		given().get(newAccountLocation, Collections.emptyMap()).then().assertThat().statusCode(200)
				.body(notNullValue());
	}
	
	@AfterClass
	public static void afterAll() {
		MoneyTransferApplication.stop();
	}
}
