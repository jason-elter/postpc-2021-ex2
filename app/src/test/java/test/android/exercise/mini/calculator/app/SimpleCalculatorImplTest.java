package test.android.exercise.mini.calculator.app;

import android.exercise.mini.calculator.app.SimpleCalculatorImpl;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class SimpleCalculatorImplTest {

    @Test
    public void when_noInputGiven_then_outputShouldBe0() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        assertEquals("0", calculatorUnderTest.output());
    }

    @Test
    public void when_inputIsPlus_then_outputShouldBe0Plus() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        calculatorUnderTest.insertPlus();
        assertEquals("0+", calculatorUnderTest.output());
    }


    @Test
    public void when_inputIsMinus_then_outputShouldBeCorrect() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        calculatorUnderTest.insertMinus();
        String expected = "0-";
        assertEquals(expected, calculatorUnderTest.output());
    }

    @Test
    public void when_callingInsertDigitWithIllegalNumber_then_exceptionShouldBeThrown() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        try {
            calculatorUnderTest.insertDigit(357);
            fail("should throw an exception and not reach this line");
        } catch (RuntimeException e) {
            // good :)
        }
    }


    @Test
    public void when_callingDeleteLast_then_lastOutputShouldBeDeleted() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // Check that 0 after deleteLast stays 0.
        calculatorUnderTest.deleteLast();
        assertEquals("0", calculatorUnderTest.output());

        // Check that operation after deleteLast is 0.
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.deleteLast();
        assertEquals("0", calculatorUnderTest.output());

        // Check that digit after deleteLast is 0.
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.deleteLast();
        assertEquals("0", calculatorUnderTest.output());

        // Check deleteLast on multi-digit number. 123 -> 12
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.deleteLast();
        assertEquals("12", calculatorUnderTest.output());

        // Check deleteLast on multi-digit number with an operation after. 12+ -> 12
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.deleteLast();
        assertEquals("12", calculatorUnderTest.output());
    }

    @Test
    public void when_callingClear_then_outputShouldBeCleared() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // Check that 0 after clear stays 0.
        calculatorUnderTest.clear();
        assertEquals("0", calculatorUnderTest.output());

        // Check clear on input that contains digits and numbers. -12-3+ -> 0
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.clear();
        assertEquals("0", calculatorUnderTest.output());
    }

    @Test
    public void when_savingState_should_loadThatStateCorrectly() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // give some input
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);

        // save current state
        Serializable savedState = calculatorUnderTest.saveState();
        assertNotNull(savedState);

        // call `clear` and make sure calculator cleared
        calculatorUnderTest.clear();
        assertEquals("0", calculatorUnderTest.output());

        // load the saved state and make sure state was loaded correctly
        calculatorUnderTest.loadState(savedState);
        assertEquals("5+7", calculatorUnderTest.output());
    }

    @Test
    public void when_savingStateFromFirstCalculator_should_loadStateCorrectlyFromSecondCalculator() {
        SimpleCalculatorImpl firstCalculator = new SimpleCalculatorImpl();
        SimpleCalculatorImpl secondCalculator = new SimpleCalculatorImpl();

        // give some input
        firstCalculator.insertDigit(5);
        firstCalculator.insertPlus();
        firstCalculator.insertDigit(7);

        // save current state
        Serializable savedState = firstCalculator.saveState();
        assertNotNull(savedState);

        // load the saved state and make sure state was loaded correctly
        firstCalculator.clear();
        secondCalculator.loadState(savedState);
        assertEquals("5+7", secondCalculator.output());
    }

    @Test
    public void when_callingInsertEquals_then_outputShouldBeResult() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 5+7-1
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(1);

        // Check output before pressing equal.
        assertEquals("5+7-1", calculatorUnderTest.output());

        // Check output after pressing equal.
        calculatorUnderTest.insertEquals();
        assertEquals("11", calculatorUnderTest.output());
    }

    @Test
    public void when_inputtingMultipleOperatorsTogether_then_ignoreExtraOperators() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 1++2+++3-+4+-5--6+-+-+-+-7
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(4);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(6);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(7);

        // Check output before pressing equal.
        assertEquals("1+2+3-4+5-6+7", calculatorUnderTest.output());

        // Check output after pressing equal. (make sure calculation didn't include extra operators)
        calculatorUnderTest.insertEquals();
        assertEquals("8", calculatorUnderTest.output());
    }

    @Test
    public void when_inputEquationWithDeleteInMiddle_then_deleteCorrectChar() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 5+7-13
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertDigit(3);

        // Delete digit
        calculatorUnderTest.deleteLast();

        // Add digits
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertDigit(5);

        // Check output.
        assertEquals("5+7-125", calculatorUnderTest.output());
    }

    @Test
    public void when_inputEquationWithClearInMiddle_then_restartEquation() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 9<Clear>12<Clear>8-7
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.clear();
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.clear();
        calculatorUnderTest.insertDigit(8);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(7);

        // Check output before pressing equal.
        assertEquals("8-7", calculatorUnderTest.output());

        // Check output after pressing equal. (make sure calculation didn't include extra stuff)
        calculatorUnderTest.insertEquals();
        assertEquals("1", calculatorUnderTest.output());
    }

    @Test
    public void when_inputEquationWithEqualsInMiddle_then_formatCorrectly() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 8-7=+4=-1
        calculatorUnderTest.insertDigit(8);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(7);
        calculatorUnderTest.insertEquals();

        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(4);
        calculatorUnderTest.insertEquals();

        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(1);

        // Check output before pressing equal.
        assertEquals("5-1", calculatorUnderTest.output());

        // Check output after pressing equal. (make sure calculation didn't include extra stuff)
        calculatorUnderTest.insertEquals();
        assertEquals("4", calculatorUnderTest.output());
    }

    @Test
    public void when_onlyInputtingDigitsAndEqual_then_outputConcatenation() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 11=22=3
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertDigit(1);
        calculatorUnderTest.insertEquals();

        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertEquals();

        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertEquals();

        // Check if output is concatenation.
        assertEquals("11223", calculatorUnderTest.output());
    }

    @Test
    public void when_savingStateTwice_should_loadLastStateCorrectly() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
        // give some input. 5+7
        calculatorUnderTest.insertDigit(5);
        calculatorUnderTest.insertPlus();
        calculatorUnderTest.insertDigit(7);

        // save current state
        Serializable savedState = calculatorUnderTest.saveState();
        assertNotNull(savedState);

        // Make changes to equation. 5+7-1
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(1);

        // save new state
        savedState = calculatorUnderTest.saveState();
        assertNotNull(savedState);

        // call `clear` and make sure calculator cleared
        calculatorUnderTest.clear();
        assertEquals("0", calculatorUnderTest.output());

        // load the saved state and make sure state was loaded correctly
        calculatorUnderTest.loadState(savedState);
        assertEquals("5+7-1", calculatorUnderTest.output());
    }

    @Test
    public void when_savingDifferentStatesFromBothCalculators_should_loadStatesCorrectlyFromEachCalculator() {
        SimpleCalculatorImpl firstCalculator = new SimpleCalculatorImpl();
        SimpleCalculatorImpl secondCalculator = new SimpleCalculatorImpl();

        // give some input to first calc
        firstCalculator.insertDigit(5);
        firstCalculator.insertPlus();
        firstCalculator.insertDigit(7);

        // give some input to second calc
        secondCalculator.insertDigit(1);
        secondCalculator.insertDigit(2);
        secondCalculator.insertMinus();
        secondCalculator.insertDigit(7);


        // save firstCalculator's current state
        Serializable firstSavedState = firstCalculator.saveState();
        assertNotNull(firstSavedState);

        // save secondCalculator's current state
        Serializable secondSavedState = secondCalculator.saveState();
        assertNotNull(secondSavedState);

        // load the saved states and make sure state was loaded correctly
        firstCalculator.clear();
        secondCalculator.clear();
        firstCalculator.loadState(secondSavedState);
        secondCalculator.loadState(firstSavedState);
        assertEquals("5+7", secondCalculator.output());
        assertEquals("12-7", firstCalculator.output());
    }

    @Test
    public void when_inputComplicatedEquationWithEqualInMiddle_then_formatCorrectly() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. 999-888-222=-333
        calculatorUnderTest.insertDigit(9);
        calculatorUnderTest.insertDigit(9);
        calculatorUnderTest.insertDigit(9);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(8);
        calculatorUnderTest.insertDigit(8);
        calculatorUnderTest.insertDigit(8);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertDigit(2);
        calculatorUnderTest.insertDigit(2);

        // Calculate and add new part to equation.
        calculatorUnderTest.insertEquals();
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertDigit(3);
        calculatorUnderTest.insertDigit(3);

        // Check output after equals.
        assertEquals("0-111-333", calculatorUnderTest.output());
    }

    @Test
    public void when_inputtingFirstNumberAsNegative_then_calculateWithZeroMinusAtStart() {
        SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();

        // give some input. -9-8-2
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(9);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(8);
        calculatorUnderTest.insertMinus();
        calculatorUnderTest.insertDigit(2);

        // Check output before equals.
        assertEquals("0-9-8-2", calculatorUnderTest.output());

        // Check output after equals.
        calculatorUnderTest.insertEquals();
        assertEquals("0-19", calculatorUnderTest.output());
    }
}