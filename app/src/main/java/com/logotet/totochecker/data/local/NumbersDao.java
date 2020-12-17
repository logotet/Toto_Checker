package com.logotet.totochecker.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NumbersDao {

    @Insert
    void insertValue(NumbersEntity value);

    @Query("SELECT*FROM numbers")
    List<NumbersEntity> getAllNumbersValues();

    @Query("SELECT*FROM numbers WHERE number_values LIKE :values")
    NumbersEntity getValueByNumbers(List<String> values);

    @Query("SELECT*FROM numbers WHERE category LIKE :category")
    List<NumbersEntity> getAllValuessByCategory(String category);

    @Query("DELETE FROM numbers WHERE number_values LIKE :values")
    void deleteValueByNumbers(List<String> values);
//@Update(onConflict = OnConflictStrategy.REPLACE)
    @Update
    void updateEntity(NumbersEntity entity);

    @Delete
    void delete(NumbersEntity entity);

    @Query("DELETE FROM numbers")
    void deleteAll();


}
