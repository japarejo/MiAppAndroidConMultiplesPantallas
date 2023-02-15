package com.example.miaplicacionmultiplespantallas.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.miaplicacionmultiplespantallas.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "titulo";
    private static final String ARG_PARAM2 = "cuerpo";
    private static final String ARG_PARAM3 = "respuestas";
    private static final String ARG_PARAM4 = "correcta";

    // TODO: Rename and change types of parameters
    private String titulo;
    private String cuerpo;
    private String[] respuestas;
    private int correcta;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String titulo, String cuerpo,String[] respuestas, int correcta) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, titulo);
        args.putString(ARG_PARAM2, cuerpo);
        args.putStringArray(ARG_PARAM3, respuestas);
        args.putInt(ARG_PARAM4, correcta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titulo = getArguments().getString(ARG_PARAM1);
            cuerpo = getArguments().getString(ARG_PARAM2);
            respuestas =getArguments().getStringArray(ARG_PARAM3);
            correcta = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result=inflater.inflate(R.layout.fragment_question, container, false);
        updateQuestion(result);
        setButtonActions(result);
        return result;
    }

    public void updateQuestion(View v){
        TextView titleTextView=(TextView)v.findViewById(R.id.Titulo);
        titleTextView.setText(titulo);
        TextView bodyTextView=(TextView)v.findViewById(R.id.Cuerpo);
        titleTextView.setText(cuerpo);
        RadioButton radioButton1=(RadioButton)v.findViewById(R.id.Opcion1);
        radioButton1.setText(respuestas[0]);
        RadioButton radioButton2=(RadioButton)v.findViewById(R.id.Opcion2);
        radioButton2.setText(respuestas[1]);
        RadioButton radioButton3=(RadioButton)v.findViewById(R.id.Opcion3);
        radioButton3.setText(respuestas[2]);
        RadioButton radioButton4=(RadioButton)v.findViewById(R.id.Opcion4);
        radioButton4.setText(respuestas[3]);
    }

    public void setButtonActions(View v){
        Button responder=(Button)v.findViewById(R.id.Responder);
        Button volver=(Button)v.findViewById(R.id.Volver);
        TextView respuesta=(TextView)v.findViewById(R.id.Resultado);
        responder.setOnClickListener(view -> {
            int opcionSeleccionada=opcionSeleccionada(v);
            if(opcionSeleccionada==-1) {
                respuesta.setText("Seleccione una respuesta");
            }else if(opcionSeleccionada==correcta){
                respuesta.setText("¡Correcto!");
            }else{
                respuesta.setText("¡Incorrecto!");
            }

        });

        volver.setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        });

    }

    public int opcionSeleccionada(View v){
        int result=-1;
        RadioButton radioButton1=(RadioButton)v.findViewById(R.id.Opcion1);
        RadioButton radioButton2=(RadioButton)v.findViewById(R.id.Opcion2);
        RadioButton radioButton3=(RadioButton)v.findViewById(R.id.Opcion3);
        RadioButton radioButton4=(RadioButton)v.findViewById(R.id.Opcion4);
        if(radioButton1.isChecked()) result=0;
        else if(radioButton2.isChecked()) result=1;
        else if(radioButton3.isChecked()) result=2;
        else if(radioButton4.isChecked()) result=3;
        return result;
    }
}