package com.canimesh.revolut.assignment.transfers.rest;

import com.canimesh.revolut.assignment.transfers.rest.dto.TransferRequest;
import com.google.gson.Gson;

import spark.Route;
import spark.Spark;

public class TransfersRestHandler {

	private final MoneyTransfersRestController transfersRestController;
	
	 public TransfersRestHandler(MoneyTransfersRestController transfersRestController) {
		this.transfersRestController = transfersRestController;
	}

	public Route createTransferRoute() {
	        return (req, res) -> {
	                TransferRequest transferRequest = new Gson().fromJson(req.body(), TransferRequest.class);
	                
	                String transferId = transfersRestController.transfer(transferRequest);
	                res.status(201);
	                res.header("location", "http://localhost:"+Spark.port()+"/transfers/"+transferId);
	                res.header("Content-Type", "application/json");
	                return "";
	        };
	    }

	    public Route getTransferDetails() {
	        return (req, res) -> {
	            String receiptId = req.params("transferId");
	            	res.status(200);
	            	res.header("Content-Type", "application/json");
	                return new Gson().toJson(transfersRestController.get(receiptId));
	        };
	    }
}
