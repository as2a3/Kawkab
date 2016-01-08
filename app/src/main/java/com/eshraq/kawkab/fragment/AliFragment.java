package com.eshraq.kawkab.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshraq.kawkab.R;
import com.eshraq.kawkab.service.CommonService;

public class AliFragment extends Fragment {

    private CommonService commonService = new CommonService(getActivity());

    public AliFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ali, container, false);

        return view;
    }

}
