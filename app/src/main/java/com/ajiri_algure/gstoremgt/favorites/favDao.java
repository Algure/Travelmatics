package com.ajiri_algure.gstoremgt.favorites;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by HP on 07/01/2019.
 */
@Dao
public interface favDao {
    @Insert
    void insertAll(favoriteClass favorite);

    @Delete
    void delete(favoriteClass favorite);

    @Query("SELECT * FROM favoriteClass")
    List<favoriteClass> getAllItems();


    @Query("SELECT * FROM favoriteClass WHERE title LIKE :cat")
    List<favoriteClass> getAllItemsinCategory(String cat);
}
