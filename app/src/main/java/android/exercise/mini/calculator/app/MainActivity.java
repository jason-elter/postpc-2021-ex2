package android.exercise.mini.calculator.app;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @VisibleForTesting
    public SimpleCalculator calculator;

    private TextView outputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (calculator == null) {
            calculator = new SimpleCalculatorImpl();
        }

        // Find all non-number views.
        TextView equalsView = findViewById(R.id.buttonEquals);
        TextView plusView = findViewById(R.id.buttonPlus);
        TextView minusView = findViewById(R.id.buttonMinus);
        TextView clearView = findViewById(R.id.buttonClear);
        View backSpaceView = findViewById(R.id.buttonBackSpace);
        outputView = findViewById(R.id.textViewCalculatorOutput);

        // Find all number views.
        int[] ids = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        TextView[] buttons = new TextView[10];
        for (int i = 0; i < 10; i++) {
            buttons[i] = findViewById(ids[i]);
        }

        // Initial update main text-view based on calculator's output.
        outputView.setText(calculator.output());

        // Set click listeners on all non-number buttons.
        equalsView.setOnClickListener(v -> {
            calculator.insertEquals();
            outputView.setText(calculator.output());
        });

        plusView.setOnClickListener(v -> {
            calculator.insertPlus();
            outputView.setText(calculator.output());
        });

        minusView.setOnClickListener(v -> {
            calculator.insertMinus();
            outputView.setText(calculator.output());
        });

        clearView.setOnClickListener(v -> {
            calculator.clear();
            outputView.setText(calculator.output());
        });

        backSpaceView.setOnClickListener(v -> {
            calculator.deleteLast();
            outputView.setText(calculator.output());
        });

        // Set click listeners on all number buttons.
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(v -> {
                calculator.insertDigit(finalI);
                outputView.setText(calculator.output());
            });
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("calculator state", calculator.saveState());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator.loadState(savedInstanceState.getSerializable("calculator state"));
        outputView.setText(calculator.output());
    }
}