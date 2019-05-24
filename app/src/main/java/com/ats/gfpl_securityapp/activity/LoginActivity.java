package com.ats.gfpl_securityapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ats.gfpl_securityapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edDSCCode;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edDSCCode = findViewById(R.id.edDSCCode);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {

        }
    }

}
