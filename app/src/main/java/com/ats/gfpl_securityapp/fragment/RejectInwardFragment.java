package com.ats.gfpl_securityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.gfpl_securityapp.R;
import com.ats.gfpl_securityapp.interfaces.RejectedInwardInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class RejectInwardFragment extends Fragment implements RejectedInwardInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reject_inward, container, false);
        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
