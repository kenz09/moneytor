package com.example.moneytor;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TransactionViewHolder> {
    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView transactionDate;
        TextView transactionType;
        TextView transactionAmount;
        TextView transactionDescription;

        TransactionViewHolder(View itemView){
            super(itemView);
            cv= (CardView)itemView.findViewById(R.id.cv);
            transactionDate = (TextView) itemView.findViewById(R.id.DetailTransactionDate);
            transactionAmount = (TextView) itemView.findViewById(R.id.DetailTransactionAmount);
            transactionType = (TextView) itemView.findViewById(R.id.DetailTransactionType);
            transactionDescription = (TextView) itemView.findViewById(R.id.DetailTransactionDescription);
        }

    }

    List <Transaction> transactions;

    RVAdapter(List<Transaction> transactions){
        this.transactions = transactions;
    }

    @Override
    public int getItemCount(){
        return transactions.size();
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup,false);
        TransactionViewHolder tvh = new TransactionViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder transactionViewHolder, int i) {
        transactionViewHolder.transactionDate.setText(transactions.get(i).getTransactionDate());
        transactionViewHolder.transactionAmount.setText(Long.toString(transactions.get(i).transactionAmount));
        transactionViewHolder.transactionType.setText(transactions.get(i).transactionType);
        transactionViewHolder.transactionDescription.setText(transactions.get(i).Description);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
