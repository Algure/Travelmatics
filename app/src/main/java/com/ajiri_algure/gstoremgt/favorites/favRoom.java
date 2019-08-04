package com.ajiri_algure.gstoremgt.favorites;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by HP on 07/01/2019.
 */
@Database(entities = {favoriteClass.class},version = 1)
public abstract class favRoom extends RoomDatabase {
    public abstract favDao getFavDao();
}
