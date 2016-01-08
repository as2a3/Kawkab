package com.eshraq.kawkab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.eshraq.kawkab.model.Home;
import com.eshraq.kawkab.model.Poem;
import com.eshraq.kawkab.model.Settings;
import com.eshraq.kawkab.model.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "kawkab.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<Settings, Integer> settingsDao = null;
    private Dao<User, Integer> userDao = null;
    private Dao<Home, Integer> homeDao = null;
    private Dao<Poem, Integer> poemDao = null;

    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    // we do this so there is only one helper
    private static DatabaseHelper helper = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void initDatabase(Context context, DataInitializeHandler initializeHandler) {
        new DataInitializeAsyncTask(context, initializeHandler).execute();
    }

    private static String getDatabaseDir(Context context) {
        return "/data/data/" + context.getPackageName() + "/databases/";
    }

    /**
     * Check whether or not database exist
     */
    public static boolean databaseInitialized(Context context) {
        boolean dbInitialized = false;
        File dbFile = new File(getDatabaseDir(context) + DATABASE_NAME);
        if (dbFile.exists()) {
            try {
                Settings settings = getHelper(context).getSettingsDao().queryForId(1);
                if (settings != null && settings.isDataInitialized()) {
                    dbInitialized = true;
                }
            } catch (SQLException e) {
            }

        }

        return dbInitialized;
    }

    /**
     * Get the helper, possibly constructing it if necessary. For each call to this method, there should be 1 and only 1
     * call to {@link #close()}.
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
        }
        usageCounter.incrementAndGet();
        return helper;
    }


    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(DatabaseHelper.class.getName(), "onUpgrade");
        onCreate(db, connectionSource);
    }

    public Dao<Home, Integer> getHomeDao() {
        if (homeDao == null) {
            try {
                homeDao = getDao(Home.class);
            } catch (SQLException e) {
            }
        }
        return homeDao;
    }

    public Dao<Settings, Integer> getSettingsDao() {
        if (settingsDao == null) {
            try {
                settingsDao = getDao(Settings.class);
            } catch (SQLException e) {
            }
        }
        return settingsDao;
    }

    public Dao<Poem, Integer> getPoemDao() {
        if (poemDao == null) {
            try {
                poemDao = getDao(Poem.class);
            } catch (SQLException e) {
            }
        }
        return poemDao;
    }

    public Dao<User, Integer> getUserDao() {
        if (userDao == null) {
            try {
                userDao = getDao(User.class);
            } catch (SQLException e) {
            }
        }
        return userDao;
    }

    /**
     * Close the database connections and clear any cached DAOs. For each call to {@link #getHelper(android.content.Context)}, there
     * should be 1 and only 1 call to this method. If there were 3 calls to {@link #getHelper(android.content.Context)} then on the 3rd
     * call to this method, the helper and the underlying database connections will be closed.
     */
    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            poemDao = null;
            helper = null;
        }
    }

    private static boolean copyDBToDataDir(Context context, String targetPath) {
        try {
            new File(targetPath).mkdir();
            InputStream is = context.getAssets().open("database/" + DATABASE_NAME);
            FileOutputStream fout = new FileOutputStream(targetPath + DATABASE_NAME);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = is.read(buffer)) != -1) {
                fout.write(buffer, 0, count);
            }

            fout.close();
            is.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static abstract class DataInitializeHandler {
        public abstract void onFinish();

        public abstract void onError();

        public abstract void onInitialized();
    }


    private static class DataInitializeAsyncTask extends AsyncTask {
        private Context mContext;
        private DataInitializeHandler mInitializeHandler;
        private boolean initialized, alreadyExists;

        public DataInitializeAsyncTask(Context context, DataInitializeHandler initializeHandler) {
            this.mContext = context;
            this.mInitializeHandler = initializeHandler;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            alreadyExists = databaseInitialized(mContext);
            if (!alreadyExists) {
                initialized = copyDBToDataDir(mContext, getDatabaseDir(mContext));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (mInitializeHandler != null) {
                if (!alreadyExists && !initialized) {
                    mInitializeHandler.onError();
                } else {
                    if (initialized) {
                        try {
                            Settings settings = getHelper(mContext).getSettingsDao().queryForId(1);
                            settings.setDataInitialized(true);
                            getHelper(mContext).getSettingsDao().update(settings);
                            mInitializeHandler.onInitialized();
                        } catch (SQLException e) {
                            mInitializeHandler.onError();
                        }
                    }

                    mInitializeHandler.onFinish();
                }
            }
        }
    }
}
