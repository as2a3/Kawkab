package com.eshraq.kawkab.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.eshraq.kawkab.R;
import com.eshraq.kawkab.model.Home;
import com.eshraq.kawkab.model.Poem;
import com.eshraq.kawkab.model.Settings;
import com.eshraq.kawkab.service.CommonService;

import java.util.List;

/**
 * Created by Ahmed on 1/8/2016.
 */
public class PoemDetailsActivity extends AppCompatActivity{
    public static final String TAG = "POEM_DETAILS";

    private CommonService commonService = new CommonService(this);
    private Settings settings;
    private List<Home> homes;
    private Poem poem;

    private TextView poemTitleTextView, poemDescriptionTextView;

    private String poemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_details);

        poemTitleTextView = (TextView) findViewById(R.id.poemTitleTextView);
        poemDescriptionTextView = (TextView) findViewById(R.id.poemDescriptionTextView);

        Intent intent = getIntent();

        poem = commonService.getPoem(intent.getIntExtra(TAG, -1));
        homes = commonService.getPoemHomes(poem.getId());
        settings = commonService.getSettings();

        if (poem.getAuthor().getId() == 1) {
            findViewById(R.id.scrollView).setBackgroundResource(R.drawable.ali_background);
        } else {
            findViewById(R.id.scrollView).setBackgroundResource(R.drawable.nassima_background);
        }

        SpannableString ss1;
        if (settings.getLanguage() == 0) {
            poemDescription = poem.getTitleAr() + "\n";

            for (int i = 0; i < homes.size(); i++) {
                poemDescription += homes.get(i).getDescriptionAr() + "\n";
                if (homes.get(i).getSeparator() != null && !homes.get(i).getSeparator().equals("")) {
                    poemDescription += homes.get(i).getSeparator() + "\n";
                }
            }

            ss1=  new SpannableString(poemDescription);
            ss1.setSpan(new RelativeSizeSpan(2f), 0,poem.getTitleAr().length(), 0); // set size
        } else {
            poemDescription = poem.getTitleSp() + "\n";

            for (int i = 0; i < homes.size(); i++) {
                poemDescription += homes.get(i).getDescriptionSp() + "\n";

                if (homes.get(i).getSeparator() != null && !homes.get(i).getSeparator().equals("")) {
                    poemDescription += homes.get(i).getSeparator() + "\n";
                }
            }

            ss1=  new SpannableString(poemDescription);
            ss1.setSpan(new RelativeSizeSpan(2f), 0, poem.getTitleSp().length(), 0); // set size

        }
        poemDescriptionTextView.setText(ss1);
    }
}
