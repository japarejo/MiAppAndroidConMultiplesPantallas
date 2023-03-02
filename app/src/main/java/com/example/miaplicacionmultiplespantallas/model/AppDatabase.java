package com.example.miaplicacionmultiplespantallas.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();
}

