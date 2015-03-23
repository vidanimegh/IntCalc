package com.example.android.intcalc.app;

/**
 * Created by MEGH on 22/3/2015.
 */
public class NegativeNumberException extends Exception {

    String field;

    NegativeNumberException(String field)
    {
        this.field = field;
    }
    public String toString()
    {
        return "Negative values are not accepted for "+field;
    }

}
