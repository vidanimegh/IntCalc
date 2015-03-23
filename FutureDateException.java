package com.example.android.intcalc.app;

/**
 * Created by MEGH on 22/3/2015.
 */
public class FutureDateException extends Exception {
    public String toString(){
        return "Future dates are not accepted";
    }
}
