package com.example.moneytor;

import java.text.DateFormat;
import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class Transaction {
    long transactionID;
    Date transactionDate;
    long transactionAmount;
    String transactionType;
    String Description;
    String person;

    private SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy");

    Transaction(long transactionID, String transactionDate, long transactionAmount,String transactionType, String Description,String person){
        this.transactionID = transactionID;
        this.transactionDate = mdformat.parse(transactionDate, new ParsePosition(0));
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.Description = Description;
        this.person=person;
    }

    public String getTransactionDate(){
        DateFormat dateInstance = mdformat.getDateInstance();
        return dateInstance.format(transactionDate);
    }
}
