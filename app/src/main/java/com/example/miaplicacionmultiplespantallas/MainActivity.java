package com.example.miaplicacionmultiplespantallas;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miaplicacionmultiplespantallas.ui.main.MainFragment;
import com.example.miaplicacionmultiplespantallas.ui.main.QuestionFragment;

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
        String titulo="PREGUNTA IMPORTANTE";
        String cuerpo="¿Te gusta Android?";
        String []opciones={"Si","No","No estoy seguro","No entiendo la pregunta"};
        int correcta=0;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, QuestionFragment.newInstance(titulo,cuerpo,opciones,correcta))
                .commitNow();
    }
}