package com.eshraq.kawkab.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eshraq.kawkab.R;
import com.eshraq.kawkab.adapter.PoemAdapter;
import com.eshraq.kawkab.model.Poem;
import com.eshraq.kawkab.service.CommonService;

import java.util.List;

public class NasemaFragment extends Fragment {

    private CommonService commonService = new CommonService(getActivity());
    private List<Poem> poems;


    private ListView listView;

    public NasemaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        poems = commonService.getUserPoems(2);


        View view = inflater.inflate(R.layout.fragment_poem, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        PoemAdapter adapter= new PoemAdapter(getActivity(), R.layout.item_poem, poems, commonService);
        listView.setAdapter(adapter);

        return view;
    }

}
