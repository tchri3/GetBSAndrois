package nati.aviran.getbs;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * Created by menachi on 03/05/2017.
 */

interface MyOnDateSetListener{
    void onDateSet(int year, int mounth, int day);
}

public class MyDatePicker extends EditText implements MyOnDateSetListener {
    int hour = 12;
    int min = 0;

    public MyDatePicker(Context context) {
        super(context);
        setInputType(0);
    }

    public MyDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInputType(0);
    }

    public MyDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInputType(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("TAG","event.getAction() == MotionEvent.ACTION_DOWN");
            MyDatePickerDialog tpd =  MyDatePickerDialog.newInstance(getId());
            //tpd.listener = this;
            tpd.show(((Activity)getContext()).getFragmentManager(),"TAG");
            return true;
        }
        return true;
    }

    @Override
    public void onDateSet(int year, int mounth, int day) {
        setText(""+day+"/"+mounth+"/"+year);
    }


    public static class MyDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private static final String ARG_CONTAINER_EDIT_TEXT_VIEW = "edit_text_container";
        MyOnDateSetListener listener;

        public static MyDatePickerDialog newInstance(int tag) {
            MyDatePickerDialog timePickerDialog = new MyDatePickerDialog();
            Bundle args = new Bundle();
            args.putInt(ARG_CONTAINER_EDIT_TEXT_VIEW, tag);
            timePickerDialog.setArguments(args);
            return timePickerDialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            Dialog datePicker = new DatePickerDialog(getActivity(),this,2000,1,1);

            if (getArguments() != null) {
                int tag = getArguments().getInt(ARG_CONTAINER_EDIT_TEXT_VIEW);
                listener = (MyOnDateSetListener) getActivity().findViewById(tag);
            }

            return datePicker;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("TAG", "dialog destroyed");
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            listener.onDateSet(i,i1,i2);
        }
    }
}



