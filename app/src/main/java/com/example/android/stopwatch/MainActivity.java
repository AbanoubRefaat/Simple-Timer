package com.example.android.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    private int seconds = 0;
    private boolean isRunning;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieving the values when the activity is recreated
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

    }

    public void onStart(View view) {

        isRunning = true;

    }

    public void onPause(View view) {

        isRunning = false;
    }

    public void onReset(View view) {

        isRunning = false;
        seconds = 0;
    }

    private void runTimer() {

        timer = (TextView) findViewById(R.id.tv_timer);
        final Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timer.setText(time);
                if (isRunning) {

                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    // saving the values of the seconds and timer state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);

    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasRunning) {
            isRunning = true;

        }
    }

    // ( for the activity partially appearing on the foreground )
//    @Override
//    protected void onPause() {
//        super.onStop();
//        wasRunning = isRunning;
//        isRunning = false;
//    }
//
    // ( for activity returning to be on foreground )
//    @Override
//    protected void onResume() {
//        super.onStart();
//        if (wasRunning) {
//            isRunning = true;
//
//        }
//    }
}
