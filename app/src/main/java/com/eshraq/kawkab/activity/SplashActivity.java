package com.eshraq.kawkab.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.eshraq.kawkab.R;
import com.eshraq.kawkab.database.DatabaseHelper;
import com.eshraq.kawkab.model.Settings;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Calendar;

public class SplashActivity extends FragmentActivity implements Runnable {

    private static final long SPLASH_WAITING_MILLIS = 3000;

    private Settings settings;
    private Dao<Settings, Integer> settingsDao;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        handler = new Handler();

        settingsDao = DatabaseHelper.getHelper(this).getSettingsDao();

        DatabaseHelper.initDatabase(this, new DatabaseHelper.DataInitializeHandler() {
            @Override
            public void onInitialized() {
                saveInstallDate();
            }

            @Override
            public void onFinish() {
                goToMainActivity();
            }

            @Override
            public void onError() {
                finish();
                Toast.makeText(getApplicationContext(), "An error occurred, please try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveInstallDate() {
        settings = getSettings();
        if (settings.getInstallDate() == 0) {
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            settings.setInstallDate(timeInMillis);
            try {
                settingsDao.update(settings);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Settings getSettings() {
        try {
            return settingsDao.queryForId(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void goToMainActivity() {
        handler.postDelayed(this, SPLASH_WAITING_MILLIS);
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // ignore orientation/keyboard change
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
