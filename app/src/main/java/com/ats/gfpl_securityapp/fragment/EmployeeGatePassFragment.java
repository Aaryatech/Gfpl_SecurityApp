package com.ats.gfpl_securityapp.fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ats.gfpl_securityapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeGatePassFragment extends Fragment implements View.OnClickListener {

    private EditText edRemark, edFrom, edTo, edTotalHrs;
    private Spinner spEmployee, spReason;
    private RadioButton rbTemp, rbDay;
    private Button btnSubmit;
    private TextView tvName;
    private CircleImageView ivPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_gate_pass, container, false);

        edRemark = view.findViewById(R.id.edRemark);
        edFrom = view.findViewById(R.id.edFrom);
        edTo = view.findViewById(R.id.edTo);
        spEmployee = view.findViewById(R.id.spEmployee);
        spReason = view.findViewById(R.id.spReason);
        edTotalHrs = view.findViewById(R.id.edTotalHrs);
        rbTemp = view.findViewById(R.id.rbTemp);
        rbDay = view.findViewById(R.id.rbDay);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        tvName = view.findViewById(R.id.tvName);

        edFrom.setOnClickListener(this);
        edTo.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edFrom) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    edFrom.setText(selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        } else if (v.getId() == R.id.edTo) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    edTo.setText(selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        } else if (v.getId() == R.id.btnSubmit) {

        }
    }


   /* public String calculateTimeDiff(String time1, String time2) {
        String diff = "";

        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date date1 = null, date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();

        diff=DurationFormatUtils.formatDuration(difference, "HH:mm");

        return diff;
    }*/


}
