package com.ats.gfpl_securityapp.fragment;


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
import android.widget.EditText;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.VisitCard;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddVisitingCardFragment extends Fragment implements View.OnClickListener {
public EditText edVisitingCardNo,edVisitingCardDesc;
public Button btnSubmit;
    VisitCard model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_visiting_card, container, false);
        edVisitingCardNo=(EditText)view.findViewById(R.id.edVisitingCardNo);
        edVisitingCardDesc=(EditText)view.findViewById(R.id.edDesc);
        btnSubmit=(Button)view.findViewById(R.id.btnSubmit);

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, VisitCard.class);
            Log.e("MODEL VISIT CARD","-----------------------------------"+model);
            edVisitingCardNo.setText(model.getCardNumber());
            edVisitingCardDesc.setText(model.getCardDesc());

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        btnSubmit.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit)
        {
            String strVisitCardNo,strVisitCardDesc;
            boolean isValidVisitCard=false,isValidVisitDesc=false;

            strVisitCardNo=edVisitingCardNo.getText().toString();
            strVisitCardDesc=edVisitingCardDesc.getText().toString();

            if (strVisitCardNo.isEmpty()) {
                edVisitingCardNo.setError("required");
            } else {
                edVisitingCardNo.setError(null);
                isValidVisitCard = true;
            }

            if(isValidVisitCard)
            {
                final VisitCard visitCard=new VisitCard(0,strVisitCardNo,strVisitCardDesc,1,1,0,0,"NA","NA");
                if(model==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to add visit card ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveVisitor(visitCard);
                            //  Log.e("Visitor", "-----------------------" + visitCard);

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
                    final VisitCard visitCard1=new VisitCard(model.getCardId(),strVisitCardNo,strVisitCardDesc,model.getDelStatus(),model.getIsActive(),model.getExInt1(),model.getExInt2(),model.getExVar1(),model.getExVar2());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do you want to edit visit card ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            saveVisitor(visitCard1);
                            //  Log.e("Visitor", "-----------------------" + visitCard);

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

    private void saveVisitor(VisitCard visitCard) {

        Log.e("PARAMETER","---------------------------------------VISITING CARD--------------------------"+visitCard);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<VisitCard> listCall = Constants.myInterface.saveVisitCard(visitCard);
            listCall.enqueue(new Callback<VisitCard>() {
                @Override
                public void onResponse(Call<VisitCard> call, Response<VisitCard> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE VISITING CARD : ", " ------------------------------SAVE VISITING CARD------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new VisitingCardListFragment(), "DashFragment");
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
                public void onFailure(Call<VisitCard> call, Throwable t) {
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

}
