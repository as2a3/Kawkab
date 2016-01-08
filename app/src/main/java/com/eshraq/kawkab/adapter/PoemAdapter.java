package com.eshraq.kawkab.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eshraq.kawkab.R;
import com.eshraq.kawkab.activity.PoemDetailsActivity;
import com.eshraq.kawkab.model.Poem;
import com.eshraq.kawkab.model.Settings;
import com.eshraq.kawkab.service.CommonService;

import java.util.List;

/**
 * Created by Ahmed on 1/8/2016.
 */
public class PoemAdapter extends ArrayAdapter{
    private CommonService commonService;
    private Settings settings;

    private List<Poem> poems;
    private Activity activity;


    public PoemAdapter(Activity activity, int resource, List<Poem> poems, CommonService commonService) {
        super(activity, resource, poems);
        this.commonService = commonService;
        settings = commonService.getSettings();
        this.activity = activity;
        this.poems = poems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_poem, null, true);

        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);

        if (settings.getLanguage() == 0) {
            titleTextView.setText(poems.get(position).getTitleAr());
        } else {
            titleTextView.setText(poems.get(position).getTitleSp());
        }



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PoemDetailsActivity.class);
                intent.putExtra(PoemDetailsActivity.TAG, poems.get(position).getId());
                activity.startActivity(intent);
            }
        });
        return view;
    }

}
