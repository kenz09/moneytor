package com.example.moneytor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_view);

        loadRecyclerView();
    }

    private void loadRecyclerView() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        RecyclerView rv = (RecyclerView) findViewById(R.id.personRecyclerView);

//        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(llm);

        loadDetailTransaction(transactions);

        PersonRVAdapter rva = new PersonRVAdapter(transactions);
        rv.setAdapter(rva);

    }

    public void loadDetailTransaction(List<Transaction> transactions){
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);

        // Gets the data repository in Read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_TRANSACTION_DATE + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // group the rows
                null,                   // filter by row groups
                sortOrder               // The sort order
        );

        if(cursor.moveToFirst()){
            transactions.add(new Transaction(cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TRANSACTION_DATE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PERSON))
            ));

            while(cursor.moveToNext()){

                transactions.add(new Transaction(cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TRANSACTION_DATE)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TRANSACTION_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PERSON))
                ));
            }
        }

        cursor.close();
    }


}
