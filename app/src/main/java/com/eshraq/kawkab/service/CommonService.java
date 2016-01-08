package com.eshraq.kawkab.service;

import android.content.Context;
import android.util.Log;

import com.eshraq.kawkab.KawkabApp;
import com.eshraq.kawkab.database.DatabaseHelper;
import com.eshraq.kawkab.model.Home;
import com.eshraq.kawkab.model.Poem;
import com.eshraq.kawkab.model.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 1/8/2016.
 */
public class CommonService {
    public Dao<Home, Integer> homeDao;
    public Dao<Poem, Integer> poemDao;
    public Dao<User, Integer> userDao;


    public CommonService(Context context) {
        homeDao = DatabaseHelper.getHelper(context).getHomeDao();
        poemDao = DatabaseHelper.getHelper(context).getPoemDao();
        userDao = DatabaseHelper.getHelper(context).getUserDao();
    }


    /////////////////////////////// Database /////////////////////////////////////

    /**
     * get user by given id
     *
     * @param userId
     * @return
     */
    public User getUser(Integer userId) {
        User user = null;
        try {
            user = userDao.queryForId(userId);
        } catch (SQLException e) {
            Log.e(KawkabApp.LOG_TAG, e.getMessage());
        }
        return user;
    }

    /**
     *
     * @param poemId
     * @return Recipe
     */
    public Poem getPoem(Integer poemId) {
        Poem poem = new Poem();

        try {
            poem = poemDao.queryForId(poemId);
        } catch (SQLException e) {
            Log.e(KawkabApp.LOG_TAG, e.getMessage());
        }
        return poem;
    }


    /**
     *
     * @param userId
     * @return List<Poem> of specific user
     */
    public List<Poem> getUserPoems(Integer userId) {
        List<Poem> poems = new ArrayList<Poem>();
        try {
            poems = poemDao.queryForEq("author_user_id", userId);
        } catch (SQLException e) {
            Log.e(KawkabApp.LOG_TAG, e.getMessage());
        }
        return poems;
    }


    /**
     *
     * @param poemId
     * @return List<Home> of specific Poem
     */
    public List<Home> getPoemHomes(Integer poemId) {
        List<Home> homes = new ArrayList<Home>();
        try {
            homes = homeDao.queryForEq("poem_id", poemId);
        } catch (SQLException e) {
            Log.e(KawkabApp.LOG_TAG, e.getMessage());
        }
        return homes;
    }

}
