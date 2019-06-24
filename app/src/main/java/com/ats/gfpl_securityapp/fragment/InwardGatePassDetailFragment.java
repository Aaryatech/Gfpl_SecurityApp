package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.adapter.MaterialDetailAdapter;
import com.ats.gfpl_securityapp.model.MaterialDetail;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InwardGatePassDetailFragment extends Fragment {
MaterialDetail model;
private RecyclerView recyclerView;
ArrayList<MaterialDetail> detailList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inward_gate_pass_detail, container, false);
        getActivity().setTitle("Material Detail");

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        try {
            String quoteStr = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(quoteStr, MaterialDetail.class);
            Log.e("MATERIAL DETAIL MODEL","-----------------------------------"+model);
            detailList.add(model);

            MaterialDetailAdapter adapter = new MaterialDetailAdapter(detailList, getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return view;
    }

}
