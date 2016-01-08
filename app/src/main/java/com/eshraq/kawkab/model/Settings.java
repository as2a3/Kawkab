package com.eshraq.kawkab.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ahmed on 1/8/2016.
 */
@DatabaseTable(tableName = "settings")
public class Settings {
    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField(columnName = "data_initialized")
    private boolean dataInitialized;

    @DatabaseField(columnName = "app_install_date")
    private long installDate;


    @DatabaseField
    private Integer language;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDataInitialized() {
        return dataInitialized;
    }

    public void setDataInitialized(boolean dataInitialized) {
        this.dataInitialized = dataInitialized;
    }

    public long getInstallDate() {
        return installDate;
    }

    public void setInstallDate(long installDate) {
        this.installDate = installDate;
    }


    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }
}
