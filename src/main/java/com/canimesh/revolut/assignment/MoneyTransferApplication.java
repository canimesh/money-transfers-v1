package com.canimesh.revolut.assignment;

import com.canimesh.revolut.assignment.account.rest.AccountsRestHandler;
import com.canimesh.revolut.assignment.common.ErrorResponse;
import com.canimesh.revolut.assignment.exception.ApplicationRuntimeException;
import com.canimesh.revolut.assignment.transfers.rest.TransfersRestHandler;
import com.google.gson.Gson;

import spark.Spark;

public class MoneyTransferApplication {

	
    public static void main(String[] args) {
    	start();
    }
    
    public static void start() {
    	start(8888);
    }
    public static void start(int port) {
    	AccountsRestHandler accountsRestHandler = DependencyInjector.instance().getAccountsRestHandler();
        TransfersRestHandler transfersRestHandler = DependencyInjector.instance().getTransfersRestHandler();
        Spark.staticFiles.location("/dist");
        Spark.port(port);
        Spark.post("/accounts", accountsRestHandler.createAccountRoute());
        Spark.get("/accounts/:accountId", accountsRestHandler.getAccountRoute());
        
        Spark.get("/transfers/:transferId", transfersRestHandler.getTransferDetails());
        Spark.post("/transfers", transfersRestHandler.createTransferRoute());
        
        Spark.get("/swagger-ui", (req,res) -> {
        	res.redirect("index.html");
        	return null;
        });
        
        Spark.exception(Exception.class, (exception, request, response) -> {
        	response.status(500);
        	response.body(new Gson().toJson(new ErrorResponse("Internal Server Error")));
        });
        
        Spark.exception(ApplicationRuntimeException.class, (exception, request, response) -> {
        	response.status(exception.getStatus());
        	response.body(exception.getMessage());
        	response.body(new Gson().toJson(new ErrorResponse(exception.getStatus(), exception.getMessage())));
        });
    }
    
    public static void stop() {
    	Spark.stop();
    }
    
    
    
}