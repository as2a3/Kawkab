package com.eshraq.kawkab.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eshraq.kawkab.R;
import com.eshraq.kawkab.database.DatabaseHelper;
import com.eshraq.kawkab.fragment.AliFragment;
import com.eshraq.kawkab.fragment.SettingFragment;
import com.eshraq.kawkab.fragment.NasemaFragment;
import com.eshraq.kawkab.model.Settings;
import com.eshraq.kawkab.service.CommonService;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CommonService commonService = new CommonService(this);
    private Dao<Settings, Integer> settingsDao;
    private Settings settings;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton languageImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsDao = DatabaseHelper.getHelper(this).getSettingsDao();
        settings = commonService.getSettings();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        languageImageButton = (ImageButton) findViewById(R.id.languageImageButton);
        languageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settings.getLanguage() == 0) {
                    settings.setLanguage(1);
                } else {
                    settings.setLanguage(0);
                }

                try {
                    settingsDao.update(settings);

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        settings = commonService.getSettings();

        setupTabIcons();
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ali_tab, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.naseema_tab, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AliFragment());
        adapter.addFrag(new NasemaFragment());
        adapter.addFrag(new SettingFragment());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }
}
