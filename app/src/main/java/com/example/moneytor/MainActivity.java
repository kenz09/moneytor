package com.example.moneytor;

import android.app.Person;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import com.example.moneytor.FeedReaderContract.FeedEntry;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    RecyclerView rv = (RecyclerView)findViewById(R.id.DetailTransactionRecyclerVIew);



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        loadRecyclerView();


    }

    private void loadRecyclerView() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        RecyclerView rv = (RecyclerView) findViewById(R.id.DetailTransactionRecyclerView);

//        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(llm);

        loadDetailTransaction(transactions);

        RVAdapter rva = new RVAdapter(transactions);
        rv.setAdapter(rva);

    }

    @Override
    protected void onResume(){
        super.onResume();
        loadRecyclerView();
    }

    public void startPersonView(View v) {
        Intent intent = new Intent(v.getContext(), PersonView.class);
        startActivity(intent);
    }


    public void fabOnClick(View v) {
        Intent intent = new Intent(v.getContext(), InputFormActivity.class);
        startActivity(intent);
    }

    public void loadDetailTransaction(List<Transaction> transactions){
        TextView totalText = (TextView) findViewById(R.id.totalText);
        TextView receiveableText = (TextView) findViewById(R.id.receiveableText);
        TextView debtText = (TextView) findViewById(R.id.debtTxt);

        Long total = new Long(0);
        Long receivable = new Long(0);
        Long debt = new Long(0);



        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);

        // Gets the data repository in Read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_TRANSACTION_DATE + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // group the rows
                null,                   // filter by row groups
                sortOrder               // The sort order
        );

        if(cursor.moveToFirst()){
            transactions.add(new Transaction(cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_TRANSACTION_DATE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_PERSON))
            ));

            Long currentAmount = cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_AMOUNT));

            if(isExpense(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE))))
             currentAmount *=-1;

            total += currentAmount;
            if(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)).equals("Loan")) receivable -=currentAmount;
            if(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)).equals("Debt")) debt +=currentAmount;


            while(cursor.moveToNext()){

                transactions.add(new Transaction(cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_TRANSACTION_DATE)),
                            cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_AMOUNT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_PERSON))
                        ));

                currentAmount = cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_AMOUNT));

                if(isExpense(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE))))
                    currentAmount *=-1;
                total += currentAmount;
                if(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)).equals("Loan")) receivable -=currentAmount;
                if(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)).equals("Debt")) debt +=currentAmount;

            }

            totalText.setText(total.toString());
            receiveableText.setText(receivable.toString());
            debtText.setText(debt.toString());
        }

        cursor.close();
    }

    private boolean isExpense(String string) {
        if(string.equals("Loan")||string.equals("Spending")) return true;
        return false;
    }
}
