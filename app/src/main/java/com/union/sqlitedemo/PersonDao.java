package com.union.sqlitedemo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Created by Nuclear on 2020/8/14
 */
@Dao
public interface PersonDao {

    @Insert
    long[] insertPersons(Person... persons);

    @Query("update person set name = :name where age = :age")
    void updatePersons(String name, int age);

    @Query("delete from person where name = :name")
    void deletePersons(String name);

    @Query("select * from person")
    List<Person> queryAllPersons();

    @Query("delete from person")
    void deleteAll();
}
