package com.example.moneytor;

public class Person {
    String name;
    long totalLoaned;
    long totalBorrowed;

    Person(String name){
        this.name=name;
        totalLoaned=totalBorrowed=0;
    }
}
