package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.interfaces.VisitorDetailInterface;

public class VisitorDetailFragment extends Fragment implements VisitorDetailInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitor_detail, container, false);


        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
