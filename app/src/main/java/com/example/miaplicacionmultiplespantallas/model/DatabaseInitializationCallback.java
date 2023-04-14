package com.example.miaplicacionmultiplespantallas.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import androidx.room.OnConflictStrategy;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializationCallback extends RoomDatabase.Callback {
    public void onCreate (SupportSQLiteDatabase db){
        String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE question (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                    "title TEXT, " +
                                    "content TEXT, " +
                                    "answer1 TEXT, " +
                                    "answer2 TEXT, " +
                                    "answer3 TEXT, " +
                                    "answer4 TEXT, " +
                                    "validAnswer INTEGER )";

        String SQL_CREATE_LECTURES_TABLE ="CREATE TABLE lecturesession ("+
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                    "details TEXT, "+
                                    "datetime INTEGER, "+
                                    "location TEXT)";
        try{
            db.execSQL(SQL_CREATE_QUESTION_TABLE);
            db.execSQL(SQL_CREATE_LECTURES_TABLE);
            Log.d("db create ","table created first time in onCreate");
        }catch(android.database.sqlite.SQLiteException ex){
            Log.d("db create ","table already exist!" +
                    "");
        }
        if(!isDbInitialized(db)) {
            List<ContentValues> questions = initialQuestions();
            for (ContentValues contentValue : questions)
                db.insert("question", OnConflictStrategy.IGNORE, contentValue);

            List<ContentValues> lectures= initialLectures();
            for(ContentValues values:lectures)
                db.insert("lecturesession",OnConflictStrategy.IGNORE, values);
        }
    }

    private List<ContentValues> initialLectures() {
        List<ContentValues> result=new ArrayList<ContentValues>();
        String[][] lessons= {
                {"Sessión del curso","168390864","En casa, es online"}
        };
        for(int i=0;i<lessons.length;i++){
            result.add(createLectureContentValues(lessons[i]));
        }
        return result;
    }

    private ContentValues createLectureContentValues(String[] lesson) {
        ContentValues result=new ContentValues();
        result.put("details",lesson[0]);
        result.put("datetime",Long.valueOf(lesson[1]));
        result.put("location",lesson[2]);
        return result;
    }


    private boolean isDbInitialized(SupportSQLiteDatabase db) {
        boolean result=false;
        Cursor c=db.query("SELECT * FROM question");
        return c.getCount()>0;
    }

    private List<ContentValues> initialQuestions() {
        List<ContentValues> result=new ArrayList<ContentValues>();
        String[][] questions={
                {"Entidades","¿Qué es una entidad?",
                    "Una clase java que define la estructura de los datos",
                    "Una interfaz que define las consultas sobre una tabla",
                    "Una clase que configura la base de datos de la aplicación",
                    "Ninguna de las anteriores es correcta"},
                {"DAOs","¿Qué es un DAO?",
                    "Una clase java que define la estructura de los datos",
                    "Una interfaz que define las consultas sobre una tabla",
                    "Una clase que configura la base de datos de la aplicación",
                    "Ninguna de las anteriores es correcta"},
                {"Material Design","¿Qué es Material Design?",
                        "Unas guías de Google para el diseño de UIs en Android",
                        "Una librería software disponible en antroid para UI",
                        "Una filosofía de diseño de interfaces de usuario",
                        "Todas las respuestas son correctas"
                }
        };
        int[] validAnswers={1,2,4};
        for(int i=0;i<questions.length;i++){
            result.add(createContentValues(questions[i],validAnswers[i]));
        }
        return result;
    }


    private ContentValues createContentValues(String[] question, int validAnswer) {
        ContentValues result=new ContentValues();
        result.put("title",question[0]);
        result.put("content",question[1]);
        result.put("answer1",question[2]);
        result.put("answer2",question[3]);
        result.put("answer3",question[4]);
        result.put("answer4",question[5]);
        result.put("validAnswer",validAnswer);
        return result;
    }


    public void onOpen (SupportSQLiteDatabase db){
        Log.d("db open ","adding db open date record");
    }

}
