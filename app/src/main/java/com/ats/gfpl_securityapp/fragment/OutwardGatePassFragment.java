package com.ats.gfpl_securityapp.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Login;
import com.ats.gfpl_securityapp.model.Outward;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.ats.gfpl_securityapp.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutwardGatePassFragment extends Fragment implements View.OnClickListener {
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    private RadioButton rbYes, rbNo;
    private RadioGroup rg;
    public EditText edOutwardName,edToName,edOutDate,edExpectedDate;
    public Button btnSubmit;
    Login loginUser;
    Outward model;
    int gatePassType,type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_outward_gate_pass, container, false);
        getActivity().setTitle("Material Gate Pass");
        edOutwardName=(EditText)view.findViewById(R.id.edOutwardName);
        edToName=(EditText)view.findViewById(R.id.edToName);
        edOutDate=(EditText)view.findViewById(R.id.edOutDate);
        edExpectedDate=(EditText)view.findViewById(R.id.edExpectedDate);

        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        rg = view.findViewById(R.id.rg);

        btnSubmit=(Button)view.findViewById(R.id.btnSubmit);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);
        Log.e("Mytag","todayString"+currentDate);

        edOutDate.setText(currentDate);
        edExpectedDate.setText(currentDate);

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("LOGIN USER : ", "--------USER-------" + loginUser);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, Outward.class);
            Log.e("MODEL","-----------------------------------"+model);

            edOutwardName.setText(model.getOutwardName());
            edToName.setText(model.getToName());
            edOutDate.setText(model.getDateOut());
            edExpectedDate.setText(model.getDateInExpected());


        }catch (Exception e)
        {
            e.printStackTrace();
        }


        if(model==null) {
            rbYes.setChecked(true);
        }else{
            type = model.getExInt1();
            if (type==1) {
                rbYes.setChecked(true);
            } else if (type==2) {
                rbNo.setChecked(true);

            }
        }


        btnSubmit.setOnClickListener(this);
        edOutDate.setOnClickListener(this);
        edExpectedDate.setOnClickListener(this);

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edOutDate) {
            int yr, mn, dy;
            if (fromDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(fromDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
            dialog.show();

        } else if (v.getId() == R.id.edExpectedDate) {
            int yr, mn, dy;
            if (toDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(toDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(getContext(), toDateListener, yr, mn, dy);
            dialog.show();

        }else if(v.getId()==R.id.btnSubmit)
        {
            String strOutwardName,strToName,strOutDate,strExpectdDate;
            boolean isValidOutward=false,isValidToName=false;
            strOutwardName=edOutwardName.getText().toString();
            strToName=edToName.getText().toString();
            strOutDate=edOutDate.getText().toString();
            strExpectdDate=edExpectedDate.getText().toString();

            if (strOutwardName.isEmpty()) {
                edOutwardName.setError("required");
            } else {
                edOutwardName.setError(null);
                isValidOutward = true;
            }

            if (strToName.isEmpty()) {
                edToName.setError("required");
            } else {
                edToName.setError(null);
                isValidToName = true;
            }

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date outDate = null;
            try {
                outDate = formatter1.parse(strOutDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String dateOut = formatter3.format(outDate);
            Log.e("Out Date","--------------------"+dateOut);

            Date expectedDate = null;
            try {
                expectedDate = formatter1.parse(strExpectdDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String dateExpected = formatter3.format(expectedDate);
            Log.e("Expected Date","--------------------"+dateExpected);

            gatePassType = 1;
            if (rbYes.isChecked()) {
                gatePassType = 1;
            } else if (rbNo.isChecked()) {
                gatePassType = 2;
            }


            if(isValidOutward && isValidToName) {
                if (model == null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final Outward outward = new Outward(0, dateOut, dateExpected, sdf.format(System.currentTimeMillis()), loginUser.getEmpId(), loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname(), strOutwardName, strToName, loginUser.getEmpId(), loginUser.getEmpId(), "", "", 3, "", "", 1, 1, gatePassType, 0, 0, "", "", "");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to submit ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveOutward(outward);

                            Log.e("Onward add", "-----------------------" + outward);

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final Outward outward = new Outward(model.getGpOutwardId(), dateOut, dateExpected, model.getDateIn(), model.getEmpId(), model.getEmpName(), strOutwardName, strToName, model.getSecIdOut(), model.getSecIdIn(), model.getOutTime(), model.getInTime(), model.getStatus(), model.getOutPhoto(), model.getInPhoto(), model.getDelStatus(), model.getIsUsed(), model.getExInt1(), model.getExInt2(), model.getExInt3(), model.getExVar1(), model.getExVar2(), model.getExVar3());

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to edit ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveOutward(outward);

                            Log.e("Onward edit", "-----------------------" + outward);

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

        }
    }

    private void saveOutward(Outward outward) {

        Log.e("PARAMETER","---------------------------------------OUTWARD SAVE--------------------------"+outward);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Outward> listCall = Constants.myInterface.saveOutwardGatepass(outward);
            listCall.enqueue(new Callback<Outward>() {
                @Override
                public void onResponse(Call<Outward> call, Response<Outward> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE OUTWARD : ", " ------------------------------SAVE OUTWARD------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new OutwardApproveFragment(), "DashFragment");
                            ft.commit();

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<Outward> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edOutDate.setText(dd + "-" + mm + "-" + yyyy);
            // tvFromDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();
        }
    };

    DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edExpectedDate.setText(dd + "-" + mm + "-" + yyyy);
            // tvToDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            toDateMillis = calendar.getTimeInMillis();
        }
    };
}
