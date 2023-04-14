package com.example.miaplicacionmultiplespantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miaplicacionmultiplespantallas.model.AppDatabase;
import com.example.miaplicacionmultiplespantallas.model.LectureSession;
import com.example.miaplicacionmultiplespantallas.model.Question;
import com.example.miaplicacionmultiplespantallas.model.DatabaseInitializationCallback;
import com.example.miaplicacionmultiplespantallas.ui.main.CalendarFragment;
import com.example.miaplicacionmultiplespantallas.ui.main.MainFragment;
import com.example.miaplicacionmultiplespantallas.ui.main.QuestionFragment;
import com.example.miaplicacionmultiplespantallas.ui.main.WebFragment;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String CHANNEL_ID = "Canal de notificaciones de repaso";
    int NOTIFICATION_ID = 7;

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

    public void Navegar(View view) {
        DatabaseInitializationCallback databaseCallback = new DatabaseInitializationCallback();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").
                addCallback(databaseCallback).
                allowMainThreadQueries().
                build();

        List<Question> questions = db.questionDao().findAll();
        if (questions.size() > 0) {
            Question q = chooseRandomQuestion(questions);
            String titulo = q.getTitle();
            String cuerpo = q.getContent();
            String[] opciones = {q.getAnswer1(), q.getAnswer2(), q.getAnswer3(), q.getAnswer4()};
            int correcta = q.getValidAnswer() - 1;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, QuestionFragment.newInstance(titulo, cuerpo, opciones, correcta))
                    .commitNow();
        } else {
            TextView tv = this.findViewById(R.id.message);
            tv.setText("No hay preguntas en la BD!");

        }
        mostrarNotificacion();
    }

    private void mostrarNotificacion() {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("No ovides repasar de vez en cuando")
                .setContentText("Está bien que te enfrentes a las preguntas, pero no olvides repasar los contenidos.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            String [] requestedPermisions={android.Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(this,requestedPermisions,23);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }else
            notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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

    public void mostrarCalendario(View view){
        DatabaseInitializationCallback databaseCallback = new DatabaseInitializationCallback();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").
                addCallback(databaseCallback).
                allowMainThreadQueries().
                build();
        List<LectureSession> sessions=db.lectureSessionDao().getSessions();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CalendarFragment.newInstance(sessions))
                .commitNow();
    }

}