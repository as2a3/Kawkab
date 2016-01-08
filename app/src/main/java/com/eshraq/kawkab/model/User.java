package com.eshraq.kawkab.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ahmed on 1/8/2016.
 */
@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
