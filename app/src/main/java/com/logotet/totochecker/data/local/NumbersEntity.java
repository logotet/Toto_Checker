package com.logotet.totochecker.data.local;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "numbers")
public class NumbersEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "number_values")
    private List<String> values;

    public NumbersEntity(List<String> values, String category) {
        this.category = category;
        this.values = values;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
