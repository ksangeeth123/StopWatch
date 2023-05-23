package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTime;
    private Button buttonStart;
    private Button buttonStop;

    private Handler handler;
    private Runnable runnable;
    private long startTime;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime = findViewById(R.id.textViewTime);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);

        handler = new Handler();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    private void startTimer() {
        if (!isRunning) {
            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);

            startTime = System.currentTimeMillis();
            isRunning = true;

            handler.postDelayed(runnable = new Runnable() {
                @Override
                public void run() {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    int seconds = (int) (elapsedTime / 1000) % 60;
                    int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
                    int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);

                    String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    textViewTime.setText(time);

                    handler.postDelayed(this, 1000);
                }
            }, 1000);
        }
    }

    private void stopTimer() {
        if (isRunning) {
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);

            handler.removeCallbacks(runnable);
            isRunning = false;
        }
    }
}
