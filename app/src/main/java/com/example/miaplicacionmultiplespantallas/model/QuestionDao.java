package com.example.miaplicacionmultiplespantallas.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT  * FROM question")
    List<Question> findAll();

    @Insert
    void insertAll(Question... questions);

    @Delete
    void delete(Question question);

}
