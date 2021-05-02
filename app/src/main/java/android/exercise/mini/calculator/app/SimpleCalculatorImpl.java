package android.exercise.mini.calculator.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleCalculatorImpl implements SimpleCalculator {

    private LinkedList<String> equation;
    private boolean onNumber;

    public SimpleCalculatorImpl() {
        super();
        this.equation = new LinkedList<>();
        this.onNumber = false;
    }

    @Override
    public String output() {
        if (equation.isEmpty()) return "0";

        StringBuilder out = new StringBuilder(isOp(equation.getFirst()) ? "0" : "");
        for (String st : equation) {
            out.append(st);
        }
        return out.toString();
    }

    @Override
    public void insertDigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("ERROR: insert digit only accepts single digit");
        }

        if (onNumber) {
            // Append digit to current number.
            equation.addLast(equation.removeLast() + digit);
        } else {
            // Add new number.
            equation.addLast(String.valueOf(digit));
            onNumber = true;
        }
    }

    @Override
    public void insertPlus() {
        if (onNumber || equation.isEmpty()) {
            equation.addLast("+");
            onNumber = false;
        }
    }

    @Override
    public void insertMinus() {
        if (onNumber || equation.isEmpty()) {
            equation.addLast("-");
            onNumber = false;
        }
    }

    @Override
    public void insertEquals() {
        int result = 0, current = 1;
        for (String st : equation) {
            char first = st.charAt(0);
            switch (first) {
                case '+':
                    current = 1;
                    break;
                case '-':
                    current = -1;
                    break;
                default:
                    result += current * Integer.parseInt(st);
            }
        }

        clear();
        onNumber = true;

        if (result < 0) {
            equation.add("-");
            equation.add(String.valueOf(-result));
        } else {
            equation.add(String.valueOf(result));
        }

    }

    @Override
    public void deleteLast() {
        if (equation.isEmpty()) return;

        String toRemove = equation.removeLast();
        int length = toRemove.length();
        if (onNumber && length > 1) {
            // Remove last digit from last number.
            equation.addLast(toRemove.substring(0, length - 1));
        } else {
            // Check if last string is a number.
            onNumber = !equation.isEmpty() && !isOp(equation.getLast());
        }
    }

    @Override
    public void clear() {
        equation.clear();
        onNumber = false;
    }

    @Override
    public Serializable saveState() {
        CalculatorState state = new CalculatorState();
        state.equation = new ArrayList<>(equation);
        state.onNumber = onNumber;
        return state;
    }

    @Override
    public void loadState(Serializable prevState) {
        if (!(prevState instanceof CalculatorState)) {
            return; // ignore
        }
        CalculatorState casted = (CalculatorState) prevState;
        equation = new LinkedList<>(casted.equation);
        onNumber = casted.onNumber;
    }

    private static class CalculatorState implements Serializable {
        /*
        all fields must only be from the types:
        - primitives (e.g. int, boolean, etc)
        - String
        - ArrayList<> where the type is a primitive or a String
        - HashMap<> where the types are primitives or a String
         */
        public ArrayList<String> equation;
        public boolean onNumber;
    }

    private static boolean isOp(String st) {
        char first = st.charAt(0);
        return first == '+' || first == '-';
    }
}
