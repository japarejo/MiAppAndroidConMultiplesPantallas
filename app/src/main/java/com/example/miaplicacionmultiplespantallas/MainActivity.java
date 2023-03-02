package com.example.miaplicacionmultiplespantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miaplicacionmultiplespantallas.model.AppDatabase;
import com.example.miaplicacionmultiplespantallas.model.Question;
import com.example.miaplicacionmultiplespantallas.model.DatabaseInitializationCallback;
import com.example.miaplicacionmultiplespantallas.ui.main.MainFragment;
import com.example.miaplicacionmultiplespantallas.ui.main.QuestionFragment;
import com.example.miaplicacionmultiplespantallas.ui.main.WebFragment;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();

        }

    }

    public void Navegar(View view){
        DatabaseInitializationCallback databaseCallback=new DatabaseInitializationCallback();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "database-name").
                                    addCallback(databaseCallback).
                                    allowMainThreadQueries().
                                    build();

        List<Question> questions=db.questionDao().findAll();
        if(questions.size()>0) {
            Question q = chooseRandomQuestion(questions);
            String titulo = q.getTitle();
            String cuerpo = q.getContent();
            String[] opciones = {q.getAnswer1(), q.getAnswer2(), q.getAnswer3(), q.getAnswer4()};
            int correcta = q.getValidAnswer() - 1;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuestionFragment.newInstance(titulo, cuerpo, opciones, correcta))
                    .commitNow();
        }else {
            TextView tv = this.findViewById(R.id.message);
            tv.setText("No hay preguntas en la BD!");

        }
    }

    private Question chooseRandomQuestion(List<Question> questions) {
        int randomIndex=new Random().nextInt(questions.size());;
        return questions.get(randomIndex);
    }

    public void repasar(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, WebFragment.newInstance("https://www.us.es"))
                .commitNow();
    }

}