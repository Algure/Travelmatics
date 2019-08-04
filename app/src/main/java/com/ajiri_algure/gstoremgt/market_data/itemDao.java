package com.ajiri_algure.gstoremgt.market_data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by HP on 29/12/2018.
 */
@Dao
public interface itemDao {

    @Insert
    void insertAll(maeketItems mitem);

    @Delete
    void delete(maeketItems mitem);

    @Query("SELECT * FROM maeketItems")
    List<maeketItems> getAllItems();


//    @Query("SELECT * FROM maeketItems WHERE category LIKE :cat")
//    List<maeketItems> getAllItemsinCategory(String cat);
}
