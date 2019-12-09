package com.canimesh.revolut.assignment.account.rest;

import com.canimesh.revolut.assignment.account.rest.dto.CreateAccountRequest;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import spark.Route;
import spark.Spark;

@RequiredArgsConstructor
public class AccountsRestHandler {

	private final AccountsRestController accountsRestController;


	public Route createAccountRoute() {
		return (req, res) -> {
				CreateAccountRequest account = new Gson().fromJson(req.body(), CreateAccountRequest.class);
				String accountId = accountsRestController.create(account);
				res.status(201);
				res.header("Content-Type", "application/json");
				res.header("location", "http://localhost:"+Spark.port()+"/accounts/" + accountId);
				return "";
		};
	}

	public Route getAccountRoute() {
		return (req, res) -> {
			String accountId = req.params("accountId");
				res.header("Content-Type", "application/json");
				return new Gson().toJson(accountsRestController.get(accountId));
		};
	}

}
