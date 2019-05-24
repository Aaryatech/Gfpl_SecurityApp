package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.ats.gfpl_securityapp.R;

public class VisitorGatePassFragment extends Fragment implements View.OnClickListener {

    private EditText edName, edMobile,edCompany,edNoOfPer;
    private Spinner spPurpose, spPerson;
    private RadioButton rbAppointment, rbRandom;
    private Button btnSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitor_gate_pass, container, false);

        edName = view.findViewById(R.id.edName);
        edMobile = view.findViewById(R.id.edMobile);
        spPurpose = view.findViewById(R.id.spPurpose);
        spPerson = view.findViewById(R.id.spPerson);
        rbAppointment = view.findViewById(R.id.rbAppointment);
        rbRandom = view.findViewById(R.id.rbRandom);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        edCompany = view.findViewById(R.id.edCompany);
        edNoOfPer = view.findViewById(R.id.edNoOfPer);

        btnSubmit.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnSubmit) {

        }

    }
}
