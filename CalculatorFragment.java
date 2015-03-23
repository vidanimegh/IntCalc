package com.example.android.intcalc.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MEGH on 22/3/2015.
 */
public class CalculatorFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener,DatePickerDialog.OnDateSetListener {

    private final String LOG_TAG = "CalculatorFragment";
    private Spinner interest_mode;
    private EditText start_dt;
    private EditText pr_amt;
    private EditText rt_int;
    private Calendar myCalendar;
    private TextView int_amt;
    private Button calc;
    private Button reset;

    public CalculatorFragment() {

    }
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        interest_mode = (Spinner) rootView.findViewById(R.id.interest_mode);
        start_dt = (EditText) rootView.findViewById(R.id.start_dt);
        start_dt.setOnFocusChangeListener(this);
        pr_amt = (EditText) rootView.findViewById(R.id.principal_amt);
        rt_int = (EditText) rootView.findViewById(R.id.rt_int);
        int_amt = (TextView) rootView.findViewById(R.id.int_amt);
        calc = (Button) rootView.findViewById(R.id.calc);
        calc.setOnClickListener(this);
        reset = (Button) rootView.findViewById(R.id.reset);
        reset.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        return rootView;
    }
    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        start_dt.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calc)
        {
            try {
            String mode = interest_mode.getSelectedItem().toString();
            Log.v(LOG_TAG,"Selected mode: "+mode);
            double p = Double.parseDouble(pr_amt.getText().toString());
            if (p<0) throw new NegativeNumberException("Principal Amount");
            double r = Double.parseDouble(rt_int.getText().toString());
            if(r<0) throw new NegativeNumberException("Rate of Interest");
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yy");

            Date start = fmt.parse(start_dt.getText().toString());
            if(start.after(new Date())) throw new FutureDateException();
            Date end = new Date();

            long n =  end.getTime() - start.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli*60;
            long hoursInMilli = minutesInMilli*60;
            long daysInMilli = hoursInMilli*24;
            long YearsInMilli = daysInMilli*365;
            long elapsedYears = n / YearsInMilli;
            long elapsedDays = n / daysInMilli;

            if(mode.equals("Yearly")) {
                calculateInterest(p,r,elapsedYears);

            }
            else if(mode.equals("Daily"))
            {
                calculateInterest(p,r,elapsedDays);
            }

            }
            catch (FutureDateException e)
            {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            }
            catch (NegativeNumberException e)
            {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
            }
            catch (NumberFormatException e)
            {
                Toast.makeText(getActivity(),"Please provide correct arguments",Toast.LENGTH_SHORT).show();
            }
            catch (ParseException e) {
                Toast.makeText(getActivity(),"Please provide the date in valid format",Toast.LENGTH_SHORT).show();
            }
        }
        else if (v.getId()==R.id.reset)
        {
            resetAll();
        }
    }

    private void calculateInterest(double p, double r, long duration) {
        double i = p * r * duration / 100;

        Double in = new Double(i);
        int_amt.setText(in.toString());
    }

    public void resetAll()
    {
        int_amt.setText("");
        start_dt.setText("");
        pr_amt.setText("");
        rt_int.setText("");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus)
        {
            new DatePickerDialog(getActivity(),this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

}
