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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PersonRVAdapter extends RecyclerView.Adapter<PersonRVAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView personName;
        TextView totalLoaned;
        TextView totalBorrowed;

        PersonViewHolder(View itemView){
            super(itemView);
            cv= (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.personNameView);
            totalLoaned = (TextView) itemView.findViewById(R.id.totalLoaned);
            totalBorrowed = (TextView) itemView.findViewById(R.id.totalBorrowed);
        }

    }

    List <Person> persons = new ArrayList<Person>();

    PersonRVAdapter(List<Transaction> transactions){
        Hashtable<String,Integer> ht= new Hashtable<String,Integer>();
        int counter=0;
        Iterator<Transaction> it = transactions.iterator();

        while(it.hasNext()){
            Transaction t = it.next();

            if(t.transactionType.equals("Income")||t.transactionType.equals("Spending")) continue;

            if(ht.get(t.person.toString())==null){
                ht.put(t.person.toString(),new Integer(counter++));
                persons.add(new Person(t.person.toString()));
            }

            Person p = persons.get(ht.get(t.person.toString()));
            if(t.transactionType.equals("Loan")) p.totalLoaned += t.transactionAmount;
            if(t.transactionType.equals("Debt")) p.totalBorrowed += t.transactionAmount;
        }

    }

    @Override
    public int getItemCount(){
        return persons.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.personcardview, viewGroup,false);
        PersonViewHolder tvh = new PersonViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder transactionViewHolder, int i) {
        transactionViewHolder.personName.setText(persons.get(i).name.toString());
        transactionViewHolder.totalLoaned.setText(Long.toString(persons.get(i).totalLoaned));
        transactionViewHolder.totalBorrowed.setText(Long.toString(persons.get(i).totalBorrowed));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
