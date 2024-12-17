package com.example.timer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    Button btn1,btn2;
    boolean isTimerRunning = false;
    CountDownTimer Timer;
    TextView TimerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this,R.raw.krishn);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        TimerText = findViewById(R.id.textView2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isTimerRunning) {
                    isTimerRunning = true;
                    startHourlyTimer();
                    Toast.makeText(MainActivity.this, "Timer Started!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimerRunning && Timer!=null){
                    Timer.cancel();
                    isTimerRunning = false;
                    if(mp.isPlaying()){
                        mp.stop();
                        mp.release();
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.krishn);
                    }
                    Toast.makeText(MainActivity.this, "Timer Stopped!", Toast.LENGTH_SHORT).show();
                    TimerText.setText("Timer Stopped");
                }
            }
        });

    }
    private void startHourlyTimer(){

        Timer = new CountDownTimer(3600 * 1000,1000 ) {
            @Override
            public void onTick(long l) {

                long seconds = (l/1000) % 60;
                long minutes = (l/(1000*60)) % 60;
                TimerText.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {

                mp.start();
                Toast.makeText(MainActivity.this, "Timer Triggered!", Toast.LENGTH_SHORT).show();

                startHourlyTimer();;
            }
        }.start();
    }
}