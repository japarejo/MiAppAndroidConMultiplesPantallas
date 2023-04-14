package com.example.miaplicacionmultiplespantallas.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface LectureSessionDao {
    @Query("SELECT * FROM lecturesession")
    List<LectureSession> getSessions();

    @Insert
    public void insert(LectureSession ... sessions);

    @Delete
    public void delete(LectureSession session);
}
