package com.union.sqlitedemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Nuclear on 2020/8/14
 */
@Entity(tableName = "person")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "age")
    private Integer age;

    public Person() {
    }

    private Person(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setAge(builder.age);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @NotNull
    @Override
    public String toString() {
        return "{\"Person\":{"
                + "\"id\":"
                + id
                + ",\"name\":\""
                + name + '\"'
                + ",\"age\":"
                + age
                + "}}";
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Integer age;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder age(Integer val) {
            age = val;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
