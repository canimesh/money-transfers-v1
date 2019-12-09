package com.canimesh.revolut.assignment.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.canimesh.revolut.assignment.MoneyTransferApplication;

import spark.Spark;

public class BaseApiTest {

	protected static final int PORT = 8888;
	protected static final String HTTP_LOCALHOST = "http://localhost:"+PORT;
	@BeforeClass
	public static void beforeAll() {
		Spark.awaitStop();
		MoneyTransferApplication.start(PORT);
	}
	
	@AfterClass
	public static void afterAll() {
		MoneyTransferApplication.stop();
	}
}
