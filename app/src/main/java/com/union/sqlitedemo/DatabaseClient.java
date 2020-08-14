package com.union.sqlitedemo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Nuclear on 2020/8/14
 */
@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class DatabaseClient extends RoomDatabase {

    public abstract PersonDao getPersonDao();
}
