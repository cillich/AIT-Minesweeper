package hu.ait.android.minesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import hu.ait.android.minesweeper.model.MinesweeperModel;
import hu.ait.android.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private Timer t;
    private TextView timerText;
    private MinesweeperView gameBoard;
    private ScrollView layoutContent;
    private Button flagModeButton;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = (MinesweeperView) findViewById(R.id.gameBoard);
        layoutContent = (ScrollView) findViewById(R.id.gameScroll);
        timerText = (TextView) findViewById(R.id.gameTimer);
        restartButton = (Button) findViewById(R.id.btnRestart);
        restartButton.setBackgroundColor(Color.WHITE);
        flagModeButton = (Button) findViewById(R.id.btnFlagMode);
        updateFlagModeButton();


        flagModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinesweeperModel.getInstance().toggleFlagMode();
                updateFlagModeButton();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBoard.clearScreen();
            }
        });

        setupTimer();
    }

    private void updateFlagModeButton() {
        if (MinesweeperModel.getInstance().isFlagModeOn()) {
            flagModeButton.setBackgroundColor(Color.RED);
            showToastMessage(getString(R.string.flagModeOn));
        } else {
            flagModeButton.setBackgroundColor(Color.WHITE);
            showToastMessage(getString(R.string.flagModeOff));
        }
    }

    public void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showFinishedGameMessage(String msg) {
        Snackbar.make(layoutContent, msg, Snackbar.LENGTH_LONG).setAction(
                R.string.btnStats, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentShowStats = new Intent(
                                MainActivity.this, StatisticsActivity.class
                        );

                        startActivity(intentShowStats);
                    }
                }
        ).show();
    }

    public void setupTimer() {
        t = new Timer();


        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!MinesweeperModel.getInstance().isWon() &&
                                !MinesweeperModel.getInstance().isLost()) {
                            MinesweeperModel.getInstance().incrementTimeCounter();
                            timerText.setText(String.valueOf(MinesweeperModel.getInstance().getTimeCounter()));
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Stops the timer when the activity stops
        t.cancel();
    }
}
