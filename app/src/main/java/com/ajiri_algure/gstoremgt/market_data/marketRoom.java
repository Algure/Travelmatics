package com.ajiri_algure.gstoremgt.market_data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by HP on 29/12/2018.
 */
@Database(entities = {maeketItems.class},version = 1)
public abstract class marketRoom extends RoomDatabase {
    public abstract itemDao getitemDao();
}
