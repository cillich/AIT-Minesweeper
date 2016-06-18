package hu.ait.android.minesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hu.ait.android.minesweeper.model.MinesweeperModel;
import hu.ait.android.minesweeper.view.MinesweeperView;

public class MenuActivity extends AppCompatActivity{


    /**
     * int that represents the game difficulty.
     * 1 = easy
     * 2 = medium
     * 3 = difficult
     */
    public int gameDifficulty = 0;

    private Button btnEasy;
    private Button btnMedium;
    private Button btnDifficult;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setBackgroundColor(Color.WHITE);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setBackgroundColor(Color.WHITE);
        btnDifficult = (Button) findViewById(R.id.btnDifficult);
        btnDifficult.setBackgroundColor(Color.WHITE);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setBackgroundColor(Color.WHITE);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMedium.setBackgroundColor(Color.WHITE);
                btnDifficult.setBackgroundColor(Color.WHITE);
                btnEasy.setBackgroundColor(Color.RED);
                btnPlay.setBackgroundColor(Color.GREEN);
                gameDifficulty = 1;
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEasy.setBackgroundColor(Color.WHITE);
                btnDifficult.setBackgroundColor(Color.WHITE);
                btnMedium.setBackgroundColor(Color.RED);
                btnPlay.setBackgroundColor(Color.GREEN);

                gameDifficulty = 2;
            }
        });

        btnDifficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEasy.setBackgroundColor(Color.WHITE);
                btnMedium.setBackgroundColor(Color.WHITE);
                btnDifficult.setBackgroundColor(Color.RED);
                btnPlay.setBackgroundColor(Color.GREEN);
                gameDifficulty = 3;

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameDifficulty != 0) {
                    MinesweeperModel.getInstance().setGameDifficulty(gameDifficulty);
                    Intent intentStartGame = new Intent(
                            MenuActivity.this, MainActivity.class
                    );
                    startActivity(intentStartGame);

                }

            }
        });



    }
}
