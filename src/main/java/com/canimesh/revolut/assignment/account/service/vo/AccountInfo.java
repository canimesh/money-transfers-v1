package com.canimesh.revolut.assignment.account.service.vo;


import lombok.Value;

@Value
public class AccountInfo {

    private String accountId;
    private String owner;
    private long amount;
}
