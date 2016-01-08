package com.eshraq.kawkab.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ahmed on 1/8/2016.
 */
@DatabaseTable(tableName = "home")
public class Home {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField(columnName = "description_ar")
    private String descriptionAr;

    @DatabaseField(columnName = "description_sp")
    private String descriptionSp;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "poem_id")
    private Poem poem;

    ////////////////////////////////////////////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionSp() {
        return descriptionSp;
    }

    public void setDescriptionSp(String descriptionSp) {
        this.descriptionSp = descriptionSp;
    }

    public Poem getPoem() {
        return poem;
    }

    public void setPoem(Poem poem) {
        this.poem = poem;
    }
}
