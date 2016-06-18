package hu.ait.android.minesweeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hu.ait.android.minesweeper.model.MinesweeperModel;

public class StatisticsActivity extends AppCompatActivity {
    private TextView gameTimeText;
    private TextView minesFoundText;
    private TextView numbersFoundText;
    private TextView emptysFoundText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        gameTimeText = (TextView) findViewById(R.id.timeStatText);
        minesFoundText = (TextView) findViewById(R.id.minesStatText);
        numbersFoundText = (TextView) findViewById(R.id.numbersStatText);
        emptysFoundText = (TextView) findViewById(R.id.emptyStatText);

        gameTimeText.setText(getString(R.string.gameTimeLabel,
                MinesweeperModel.getInstance().getTimeCounter()));
        minesFoundText.setText(getString(R.string.gameMinesLabel,
                MinesweeperModel.getInstance().gameBoardCounter(MinesweeperModel.FLAG)));
        numbersFoundText.setText(getString(R.string.gameNumbersLabel,
                MinesweeperModel.getInstance().gameBoardCounter(MinesweeperModel.NUMBER)));
        emptysFoundText.setText(getString(R.string.gameEmptysLabel,
                MinesweeperModel.getInstance().gameBoardCounter(MinesweeperModel.EMPTY)));

    }
}
