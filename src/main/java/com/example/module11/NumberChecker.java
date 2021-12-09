package com.example.module11;

public class NumberChecker {
    private double num;

    public NumberChecker(double num){
        this.num = num;
    }

    public double numGetter(){
        return this.num;
    }

    public boolean isPrimeNumber(){
        if (num <= 1)
            return false;

        for (int i = 2; i < num; i++)
            if (num % i == 0){
                return false;
            }

        return true;
    }
}
