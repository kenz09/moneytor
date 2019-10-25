package com.example.moneytor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.moneytor.FeedReaderContract.FeedEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InputFormActivity extends AppCompatActivity {
    Boolean incomeToggle;
    Boolean spendingToggle;
    Boolean loanToggle;
    Boolean debtToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_input_form);

        resetFlag();

    }

    private void resetFlag() {
        Button incomeBtn = findViewById(R.id.incomeBtn);
        Button spendingBtn = findViewById(R.id.spendingBtn);
        Button debtBtn = findViewById(R.id.debtBtn);
        Button loanBtn = findViewById(R.id.loanBtn);

        incomeBtn.setBackgroundColor(Color.LTGRAY);
        spendingBtn.setBackgroundColor(Color.LTGRAY);
        debtBtn.setBackgroundColor(Color.LTGRAY);
        loanBtn.setBackgroundColor(Color.LTGRAY);

        incomeToggle = false;
        spendingToggle = false;
        loanToggle = false;
        debtToggle = false;

        LinearLayout recipientHL = (LinearLayout) findViewById(R.id.recipientHorizontalLayout);
        recipientHL.setVisibility(View.GONE);

    }

    public void saveBtn_OnClick(View view) {
        EditText nominalInput = (EditText) findViewById(R.id.nominalField);
        EditText descripitionInput = (EditText) findViewById(R.id.descriptionField);
        EditText recipientInput = (EditText) findViewById(R.id.recipientField);
       if(!(nominalInput.getText().equals("")||nominalInput.getText()==null)&&(incomeToggle||spendingToggle||loanToggle||debtToggle)){
           FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);

           // Gets the data repository in write mode
           SQLiteDatabase db = dbHelper.getWritableDatabase();

           // Create a new map of values, where column names are the keys
           ContentValues values = new ContentValues();
           values.put(FeedEntry.COLUMN_NAME_TRANSACTION_DATE, getCurrentDate());
           values.put(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE, getTransactionType());
           values.put(FeedEntry.COLUMN_NAME_AMOUNT, Integer.parseInt(nominalInput.getText().toString()));
           if(descripitionInput.getText().toString().equals(""))
               values.put(FeedEntry.COLUMN_NAME_DESCRIPTION,"No Description");
           else
               values.put(FeedEntry.COLUMN_NAME_DESCRIPTION,descripitionInput.getText().toString());
           if(recipientInput.getText().toString().equals("")||incomeToggle||spendingToggle)
               values.put(FeedEntry.COLUMN_NAME_PERSON,"No Recipient");
           else
               values.put(FeedEntry.COLUMN_NAME_PERSON,recipientInput.getText().toString());

           // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
           finish();
       }
    }

    private String getTransactionType() {
        if (incomeToggle) return "Income";
        if (spendingToggle) return "Spending";
        if (loanToggle) return "Loan";
        if (debtToggle) return "Debt";
        return "-";
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy");
        return mdformat.format(calendar.getTime());
    }

    public void debtBtn_OnClick(View view) {
        Button debtBtn = (Button) findViewById(R.id.debtBtn);
        resetFlag();
        debtBtn.setBackgroundColor(Color.WHITE);

        LinearLayout recipientHL = (LinearLayout) findViewById(R.id.recipientHorizontalLayout);
        recipientHL.setVisibility(View.VISIBLE);

        debtToggle = true;
    }

    public void loanBtn_OnClick(View view) {
        Button loanBtn = (Button) findViewById(R.id.loanBtn);
        resetFlag();
        loanBtn.setBackgroundColor(Color.WHITE);

        LinearLayout recipientHL = (LinearLayout) findViewById(R.id.recipientHorizontalLayout);
        recipientHL.setVisibility(View.VISIBLE);

        loanToggle = true;
    }

    public void spendingBtn_OnClick(View view) {
        Button spendingBtn = (Button) findViewById(R.id.spendingBtn);
        resetFlag();
        spendingBtn.setBackgroundColor(Color.WHITE);

        spendingToggle = true;
    }

    public void incomeBtn_OnClick(View view) {
        Button incomeBtn = (Button) findViewById(R.id.incomeBtn);
        resetFlag();
        incomeBtn.setBackgroundColor(Color.WHITE);

        incomeToggle = true;
    }
}
