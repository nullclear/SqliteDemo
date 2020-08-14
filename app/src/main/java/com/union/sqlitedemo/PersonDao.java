package com.union.sqlitedemo;

import androidx.room.*;

import java.util.List;

/**
 * Created by Nuclear on 2020/8/14
 */
@Dao
public interface PersonDao {

    @Insert
    long[] insertPersons(Person... persons);

    @Update
    void updatePersons(Person... persons);

    @Delete
    void deletePersons(Person... persons);

    @Query("select * from person")
    List<Person> queryAllPersons();

}
