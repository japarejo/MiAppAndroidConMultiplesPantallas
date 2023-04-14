package com.example.miaplicacionmultiplespantallas.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CalendarView;

import com.example.miaplicacionmultiplespantallas.R;
import com.example.miaplicacionmultiplespantallas.model.LectureSession;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    List<LectureSession> sessions;

    public CalendarFragment() {
        // Required empty public constructor
    }


    public List<LectureSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<LectureSession> sessions) {
        this.sessions = sessions;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */

    public static CalendarFragment newInstance(List<LectureSession> sessions) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setSessions(sessions);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result= inflater.inflate(R.layout.fragment_calendar, container, false);
        Button volver=(Button)result.findViewById(R.id.button4);
        volver.setOnClickListener(
                (v) -> volverAfragmentoPrincipal()
        );

        CalendarView calendarView=(CalendarView)result.findViewById(R.id.calendarView);
        Calendar calendar= Calendar.getInstance();
        if(sessions!=null && !sessions.isEmpty())
        {
            LocalDateTime d=sessions.get(0).getDateTime();
            calendar.set(d.getYear(),d.getMonthValue(),d.getDayOfMonth());
        }else
            calendar.set(2023,2,31);
        calendarView.setDate(calendar.getTimeInMillis());
        return result;
    }

    public void volverAfragmentoPrincipal(){
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();

    }
}