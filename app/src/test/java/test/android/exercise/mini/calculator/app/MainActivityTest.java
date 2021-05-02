package test.android.exercise.mini.calculator.app;

import android.exercise.mini.calculator.app.MainActivity;
import android.exercise.mini.calculator.app.R;
import android.exercise.mini.calculator.app.SimpleCalculator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.io.Serializable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class MainActivityTest {

    private static final String DEFAULT_CALCULATOR_OUTPUT = "~~~ ready to start ~~~";

    private ActivityController<MainActivity> activityController;
    private MainActivity activityUnderTest;
    private SimpleCalculator mockCalculator;

    /**
     * initialize main activity with a mock calculator
     */
    @Before
    public void setup() {
        mockCalculator = Mockito.mock(SimpleCalculator.class);
        Mockito.when(mockCalculator.output()).thenReturn(DEFAULT_CALCULATOR_OUTPUT);

        activityController = Robolectric.buildActivity(MainActivity.class);
        activityUnderTest = activityController.get();
        activityUnderTest.calculator = mockCalculator;
        activityController.create().start().resume();
    }

    @Test
    public void when_settingUpTheActivity_then_itShouldShowTheExpectedCalculatorOutputRightAway() {
        // setup
        String expectedText = DEFAULT_CALCULATOR_OUTPUT;
        TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
        // verify
        assertEquals(expectedText, activityMainTextView.getText().toString());
    }

    @Test
    public void when_userClicksButtonPlus_then_activityShouldForwardCallToCalculator_and_ShowTheExpectedCalculatorOutputRightAway() {
        // setup
        String expectedText = "button PLUS clicked";
        Mockito.when(mockCalculator.output()).thenReturn(expectedText);

        TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
        View buttonPlus = activityUnderTest.findViewById(R.id.buttonPlus);

        // test
        buttonPlus.performClick();

        // verify
        Mockito.verify(mockCalculator).insertPlus(); // make sure that the activity called this method
        assertEquals(expectedText, activityMainTextView.getText().toString()); // make sure that the activity shows text from calculator.output()
    }

    @Test
    public void when_userClicksButtonMinus_then_activityShouldForwardCallToCalculator_and_ShowTheExpectedCalculatorOutputRightAway() {
        // setup
        String expectedText = "button MINUS clicked";
        Mockito.when(mockCalculator.output()).thenReturn(expectedText);

        TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
        View buttonMinus = activityUnderTest.findViewById(R.id.buttonMinus);

        // test
        buttonMinus.performClick();

        // verify
        Mockito.verify(mockCalculator).insertMinus(); // make sure that the activity called this method
        assertEquals(expectedText, activityMainTextView.getText().toString()); // make sure that the activity shows text from calculator.output()
    }

    @Test
    public void when_userClicksButtonEquals_then_activityShouldForwardCallToCalculator_and_ShowTheExpectedCalculatorOutputRightAway() {
        // setup
        String expectedText = "button EQUALS clicked";
        Mockito.when(mockCalculator.output()).thenReturn(expectedText);

        TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
        View buttonEquals = activityUnderTest.findViewById(R.id.buttonEquals);

        // test
        buttonEquals.performClick();

        // verify
        Mockito.verify(mockCalculator).insertEquals(); // make sure that the activity called this method
        assertEquals(expectedText, activityMainTextView.getText().toString()); // make sure that the activity shows text from calculator.output()
    }

    @Test
    public void when_userClicksButtonClear_then_activityShouldForwardCallToCalculator_and_ShowTheExpectedCalculatorOutputRightAway() {
        // setup
        String expectedText = "button CLEAR clicked";
        Mockito.when(mockCalculator.output()).thenReturn(expectedText);

        TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
        View buttonClear = activityUnderTest.findViewById(R.id.buttonClear);

        // test
        buttonClear.performClick();

        // verify
        Mockito.verify(mockCalculator).clear(); // make sure that the activity called this method
        assertEquals(expectedText, activityMainTextView.getText().toString()); // make sure that the activity shows text from calculator.output()
    }

    @Test
    public void when_userClicksButtonBackSpace_then_activityShouldForwardCallToCalculator_and_ShowTheExpectedCalculatorOutputRightAway() {
        // setup
        String expectedText = "button BACKSPACE clicked";
        Mockito.when(mockCalculator.output()).thenReturn(expectedText);

        TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
        View buttonBackSpace = activityUnderTest.findViewById(R.id.buttonBackSpace);

        // test
        buttonBackSpace.performClick();

        // verify
        Mockito.verify(mockCalculator).deleteLast(); // make sure that the activity called this method
        assertEquals(expectedText, activityMainTextView.getText().toString()); // make sure that the activity shows text from calculator.output()
    }

    @Test
    public void when_userClicksButtonI_then_activityShouldForwardCallToCalculator_and_ShowTheExpectedCalculatorOutputRightAway() {
        int[] ids = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

        for (int i = 0; i < 10; i++) {
            // setup
            String expectedText = "button " + i + " clicked";
            Mockito.when(mockCalculator.output()).thenReturn(expectedText);

            TextView activityMainTextView = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
            View currentButton = activityUnderTest.findViewById(ids[i]);

            // test
            currentButton.performClick();

            // verify
            Mockito.verify(mockCalculator).insertDigit(i); // make sure that the activity called this method
            assertEquals(expectedText, activityMainTextView.getText().toString()); // make sure that the activity shows text from calculator.output()
        }
    }

    @Test
    public void when_activityGetsStateSaved_then_shouldAlsoSaveCalculatorState() {
        // setup
        Serializable dummyState = new Serializable() {
        };
        Mockito.when(mockCalculator.saveState()).thenReturn(dummyState);

        Bundle spyBundle = Mockito.spy(new Bundle());

        // test
        activityController.saveInstanceState(spyBundle);

        // verify
        Mockito.verify(spyBundle).putSerializable(anyString(), eq(dummyState)); // make sure that the activity stored the calculator state into the spy bundle
    }

    @Test
    public void when_activityGetsStateRestored_then_shouldAlsoSaveCalculatorState() {
        // setup
        Serializable dummyState = new Serializable() {
        };
        Mockito.when(mockCalculator.saveState()).thenReturn(dummyState);

        // let the activity store the calculator state into the bundle
        Bundle spyBundle = Mockito.spy(new Bundle());
        activityController.saveInstanceState(spyBundle);

        // test
        activityController.restoreInstanceState(spyBundle);

        // verify
        Mockito.verify(mockCalculator).loadState(eq(dummyState)); // make sure that the activity passed the previously-stored state to the calculator to load
    }
}