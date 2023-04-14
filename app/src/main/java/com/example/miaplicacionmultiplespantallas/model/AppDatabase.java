package com.example.miaplicacionmultiplespantallas.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Question.class,LectureSession.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();

    public abstract LectureSessionDao lectureSessionDao();
}

