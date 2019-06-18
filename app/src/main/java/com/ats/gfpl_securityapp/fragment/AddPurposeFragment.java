package com.ats.gfpl_securityapp.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.EmployeeAdapter;
import com.ats.gfpl_securityapp.constants.Constants;
import com.ats.gfpl_securityapp.model.Employee;
import com.ats.gfpl_securityapp.model.Purpose;
import com.ats.gfpl_securityapp.model.PurposeList;
import com.ats.gfpl_securityapp.utils.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPurposeFragment extends Fragment implements View.OnClickListener {
    private EditText edPurHeading,edDescription,edRemark,edPassDuration,edEmployee;
    private TextView tvEmp;
    private Spinner spType;
    private TextInputLayout textEmp;
    private Button btnSubmit;
    String type;
    PurposeList model;
    ArrayList<String> typeArray = new ArrayList<>();
    ArrayList<Integer> typeIdArray = new ArrayList<>();

    //Assigne Employee
    public RecyclerView recyclerView;
    private EmployeeAdapter mAdapter;
    String empId="";
    String strEmpId;
    String stringId,stringName;
    ArrayList<Employee> empList = new ArrayList<>();
    public static ArrayList<Employee> assignEmpStaticList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_purpose, container, false);

        edPurHeading=(EditText)view.findViewById(R.id.edPurposHeading);
        edDescription=(EditText)view.findViewById(R.id.edDescription);
        edRemark=(EditText)view.findViewById(R.id.edRemark);
        edPassDuration=(EditText)view.findViewById(R.id.edPassDuration);
        tvEmp=(TextView) view.findViewById(R.id.tvEmp);
        edEmployee=(EditText) view.findViewById(R.id.edEmployee);
        textEmp=(TextInputLayout) view.findViewById(R.id.textEmployee);
        spType=(Spinner)view.findViewById(R.id.spType);
        btnSubmit=(Button)view.findViewById(R.id.btnSubmit);

        typeArray.add("Select Type");
        typeArray.add("Type 1");
        typeArray.add("Type 2");

        typeIdArray.add(0);
        typeIdArray.add(1);
        typeIdArray.add(2);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeArray);
        spType.setAdapter(typeAdapter);

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, PurposeList.class);
            Log.e("Purpose edit Model", "----------------------------" + model);
            edPurHeading.setText(model.getPurposeHeading());
            edDescription.setText(model.getDescription());
            edRemark.setText(model.getRemark());
            edPurHeading.setText(model.getPurposeHeading());
            if(model.getPurposeType()==2)
            {
                edEmployee.setText(model.getAssignEmpName());
            }

            if (model != null) {
                int position = 0;
                if (typeIdArray.size() > 0) {
                    for (int i = 0; i < typeIdArray.size(); i++) {
                        if (model.getPurposeType() == typeIdArray.get(i)) {
                            position = i;
                            break;
                        }
                    }
                    spType.setSelection(position);

                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(model==null)
        {
            getActivity().setTitle("Add Purpose");
        }else{
            getActivity().setTitle("Edit Purpose");
        }

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 type = typeArray.get(position);
                if(type.equalsIgnoreCase("Type 1"))
                {
                    tvEmp.setVisibility(View.GONE);
                    textEmp.setVisibility(View.GONE);
                    edEmployee.setVisibility(View.GONE);
                }else if(type.equalsIgnoreCase("Type 2")){
                    tvEmp.setVisibility(View.VISIBLE);
                    textEmp.setVisibility(View.VISIBLE);
                    edEmployee.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getEmployee();

        btnSubmit.setOnClickListener(this);
        tvEmp.setOnClickListener(this);

        return view;
    }

    private void getEmployee() {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<Employee>> listCall = Constants.myInterface.allEmployees();
            listCall.enqueue(new Callback<ArrayList<Employee>>() {
                @Override
                public void onResponse(Call<ArrayList<Employee>> call, Response<ArrayList<Employee>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("EMPLOYEE LIST : ", " -----------------------------------EMPLOYEE LIST---------------------------- " + response.body());
                            empList.clear();
                            empList=response.body();

                            assignEmpStaticList.clear();
                            assignEmpStaticList = empList;

                            for (int i = 0; i < assignEmpStaticList.size(); i++) {
                                assignEmpStaticList.get(i).setChecked(false);
                            }

//                            String employeeId = strEmpId;
//
//                            List<String> list = Arrays.asList(employeeId.split("\\s*,\\s*"));
//
//                            Log.e("LIST", "----------------------" + list);
//
//                            // assignStaticList.clear();
//                            for (int j = 0; j < assignEmpStaticList.size(); j++) {
//
//                                for (int k = 0; k < list.size(); k++) {
//
//                                    if (assignEmpStaticList.get(j).getEmpId() == Integer.parseInt(list.get(k))) {
//
//                                        assignEmpStaticList.get(j).setChecked(true);
//                                        // assignStaticList.add(empList.get(j));
//
//                                    }
//                                }
//                            }

                            String strEmpId="";
                            if (model != null) {
                                strEmpId = model.getEmpId();
                            }

                            List<String> list = Arrays.asList(strEmpId.split("\\s*,\\s*"));

                            Log.e("LIST", "----------------------" + list);

                            // assignStaticList.clear();
                            for (int j = 0; j < assignEmpStaticList.size(); j++) {

                                for (int k = 0; k < list.size(); k++) {

                                    if (assignEmpStaticList.get(j).getEmpId() == Integer.parseInt(list.get(k))) {

                                        assignEmpStaticList.get(j).setChecked(true);
                                        // assignStaticList.add(empList.get(j));

                                    }
                                }
                            }

                            getAssignUser();

                            mAdapter = new EmployeeAdapter(assignEmpStaticList, getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Employee>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tvEmp)
        {
            new EmpListDialog(getContext()).show();
        }else if(v.getId()==R.id.btnSubmit)
        {
            if(type.equalsIgnoreCase("Type 1")) {
                String strHeading, strDescription, strRemark, strPassDuration;
                boolean isValidHeading = false, isValidDescription = false, isValidRemark = false, isValidPassDuration = false;
                strHeading = edPurHeading.getText().toString();
                strDescription = edDescription.getText().toString();
                strRemark = edRemark.getText().toString();
                strPassDuration = edPassDuration.getText().toString();

                if (strHeading.isEmpty()) {
                    edPurHeading.setError("required");
                } else {
                    edPurHeading.setError(null);
                    isValidHeading = true;
                }

                if (strDescription.isEmpty()) {
                    edDescription.setError("required");
                } else {
                    edDescription.setError(null);
                    isValidDescription = true;
                }

                if (strRemark.isEmpty()) {
                    edRemark.setError("required");
                } else {
                    edRemark.setError(null);
                    isValidRemark = true;
                }
//                if (strPassDuration.isEmpty()) {
//                    edPassDuration.setError("required");
//                } else {
//                    edPassDuration.setError(null);
//                    isValidPassDuration = true;
//                }
                if (isValidHeading && isValidDescription && isValidRemark) {

                   if(model!=null)
                   {
                       final Purpose purpose = new Purpose(model.getPurposeId(), strHeading, 1, strDescription, strRemark, model.getEmpId(), "NA", "NA", model.getDelStatus(), model.getIsUsed(), 0, 0, 0, null, null, null);
                       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                       builder.setTitle("Confirmation");
                       builder.setMessage("Do you want to edit purpose ?");
                       builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               savePurpose(purpose);
                               Log.e("PURPOSE EDIT TYPE1","-----------------------"+purpose);

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
                   }else {
                       final Purpose purpose = new Purpose(0, strHeading, 1, strDescription, strRemark, "0", "NA", "NA", 1, 1, 0, 0, 0, null, null, null);
                       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                       builder.setTitle("Confirmation");
                       builder.setMessage("Do you want to add purpose ?");
                       builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               savePurpose(purpose);
                               Log.e("PURPOSE ADD TYPE1","-----------------------"+purpose);

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
            if(type.equalsIgnoreCase("Type 2"))
            {
                String strHeading, strDescription, strRemark, strPassDuration;
                boolean isValidHeading = false, isValidDescription = false, isValidRemark = false, isValidPassDuration = false;
                strHeading = edPurHeading.getText().toString();
                strDescription = edDescription.getText().toString();
                strRemark = edRemark.getText().toString();
                strPassDuration = edPassDuration.getText().toString();
               // strEmployee = edEmp.getText().toString();

                if (strHeading.isEmpty()) {
                    edPurHeading.setError("required");
                } else {
                    edPurHeading.setError(null);
                    isValidHeading = true;
                }
                if (strDescription.isEmpty()) {
                    edDescription.setError("required");
                } else {
                    edDescription.setError(null);
                    isValidDescription = true;
                }
                if (strRemark.isEmpty()) {
                    edRemark.setError("required");
                } else {
                    edRemark.setError(null);
                    isValidRemark = true;
                }

//                if (strPassDuration.isEmpty()) {
//                    edPassDuration.setError("required");
//                } else {
//                    edPassDuration.setError(null);
//                    isValidPassDuration = true;
//                }

                if (isValidHeading && isValidDescription && isValidRemark ) {

                    if (model != null) {
                        final Purpose purpose = new Purpose(model.getPurposeId(), strHeading, 2, strDescription, strRemark, stringId, "NA", "NA", model.getDelStatus(), model.getIsUsed(), model.getExInt1(), model.getExInt2(), model.getExInt3(), null, null, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Do you want to edit purpose ?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                savePurpose(purpose);
                                Log.e("PURPOSE EDIT TYPE2","-----------------------"+purpose);
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

                    } else {

                        final Purpose purpose = new Purpose(0, strHeading, 2, strDescription, strRemark, stringId, "NA", "NA", 1, 1, 0, 0, 0, null, null, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Do you want to add purpose ?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                savePurpose(purpose);
                                Log.e("PURPOSE ADD TYPE2","-----------------------"+purpose);
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
    }

    private void savePurpose(Purpose purpose) {

        Log.e("PARAMETER","---------------------------------------PURPOSE--------------------------"+purpose);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Purpose> listCall = Constants.myInterface.savePurpose(purpose);
            listCall.enqueue(new Callback<Purpose>() {
                @Override
                public void onResponse(Call<Purpose> call, Response<Purpose> response) {
                    try {
                        if (response.body() != null) {

                            assignEmpStaticList.clear();
                            Log.e("SAVE PURPOSE : ", " ------------------------------SAVE PURPOSE------------------------- " + response.body());
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, new PurposeListFragment(), "DashFragment");
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
                public void onFailure(Call<Purpose> call, Throwable t) {
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

    private class EmpListDialog extends Dialog {

        public Button btnCancel,btnSubmit;

        public EmpListDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_emp_list);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            try {
                mAdapter = new EmployeeAdapter(assignEmpStaticList, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    getAssignUser();

                }
            });

        }

    }
    private void getAssignUser() {

        ArrayList<Employee> assignedEmpArray = new ArrayList<>();
        ArrayList<Integer> assignedEmpIdArray = new ArrayList<>();
        ArrayList<String> assignedEmpNameArray = new ArrayList<>();

        if (assignEmpStaticList != null) {
            if (assignEmpStaticList.size() > 0) {
                assignedEmpArray.clear();
                for (int i = 0; i < assignEmpStaticList.size(); i++) {
                    if (assignEmpStaticList.get(i).getChecked()) {
                        assignedEmpArray.add(assignEmpStaticList.get(i));
                        assignedEmpIdArray.add(assignEmpStaticList.get(i).getEmpId());
                        assignedEmpNameArray.add(assignEmpStaticList.get(i).getEmpFname()+ " "+assignEmpStaticList.get(i).getEmpMname()+ " "+assignEmpStaticList.get(i).getEmpSname());
                    }
                }
            }
            Log.e("ASSIGN EMP","---------------------------------"+assignedEmpArray);
            Log.e("ASSIGN EMP SIZE","---------------------------------"+assignedEmpArray.size());

            String empIds=assignedEmpIdArray.toString().trim();
            Log.e("ASSIGN EMP ID","---------------------------------"+empIds);

             stringId = ""+empIds.substring(1, empIds.length()-1).replace("][", ",")+"";

            Log.e("ASSIGN EMP ID STRING","---------------------------------"+stringId);

            String empName=assignedEmpNameArray.toString().trim();
            Log.e("ASSIGN EMP NAME","---------------------------------"+empName);

             stringName = ""+empName.substring(1, empName.length()-1).replace("][", ",")+"";

            Log.e("ASSIGN EMP NAME STRING","---------------------------------"+stringName);
            edEmployee.setText(stringName);
        }
//        try {
//            for (int i=0;i<assignedEmpArray.size();i++)
//            {
//                empId= empId+ ","+ String.valueOf(assignedEmpArray.get(i).getEmpId());
//
//            }
//            Log.e("Id","--------------------------EMP ID--------------------------"+empId);
//
//            strEmpId = empId.substring(1);
//            Log.e("Id--","--------------------------EMP ID--------------------------"+strEmpId);
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }

}
