package com.eshraq.kawkab.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ahmed on 1/8/2016.
 */
@DatabaseTable(tableName = "poem")
public class Poem {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField(columnName = "title_ar")
    private String titleAr;

    @DatabaseField(columnName = "title_sp")
    private String title_sp;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "author_user_id")
    private User author;


    //////////////////////////////////////////////////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getTitle_sp() {
        return title_sp;
    }

    public void setTitle_sp(String title_sp) {
        this.title_sp = title_sp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
