package com.example.math5_web.InterpolationMethods;

import com.example.math5_web.Coordinates.Coordinate;

import java.util.List;
import java.util.function.UnaryOperator;

public class NewtonMethod extends InterpolationMethod {
    private UnaryOperator<Double> function;
    private double[][] divDifferences;

    public NewtonMethod(List<Coordinate> coords) {
        super(coords);
    }

    public NewtonMethod() {
        super();
    }

    public double[][] getDivDifferences(){
        return divDifferences;
    }

    public double interpolate(double input) {
        if (isEquallySpaced()) {
            return interpolateEquallySpacedFunction(input);
        } else {
            function = this::interpolateNotEquallySpacedFunction;
            return interpolateNotEquallySpacedFunction(input);
        }
    }

    public UnaryOperator<Double> getFunction() {
        if (function == null) {
            //initDifferences();
            throw new NullPointerException();
        }
        return function;
    }

    private boolean isEquallySpaced() {
        double difference = coords.get(1).getX() - coords.get(0).getX();
        for (int i = 0; i < coords.size() - 1; i++) {
            var a = coords.get(i + 1).getX() - coords.get(i).getX();
            if (a - difference > 0.00000001) return false;
        }
        return true;
    }

    public double interpolateEquallySpacedFunction(double input) {
        initDifferences(true);
        if (isInputAtTheEnd(input)) {
            function = this::interpolateBackward;
            return interpolateBackward(input);
        } else {
            function = this::interpolateForward;
            return interpolateForward(input);
        }
    }


    private double interpolateBackward(double input) {
        double h = coords.get(1).getX() - coords.get(0).getX();
        double t = (input - coords.get(coords.size() - 1).getX()) / h;
        double sum = 0;
        for (int i = coords.size() - 2; i >= 0; i--) {
            sum += multK(t, coords.size() - i - 1, false) * divDifferences[i][coords.size() - i - 1] / fac(coords.size() - i - 1);
        }
        return coords.get(coords.size() - 1).getY() + sum;
    }


    private double interpolateForward(double input) {
        double h = coords.get(1).getX() - coords.get(0).getX();
        double t = (input - coords.get(0).getX()) / h;
        double sum = 0;
        for (int i = 1; i < coords.size(); i++) {
            sum += multK(t, i, true) * findDiffForZero(i) / fac(i);
        }
        return coords.get(0).getY() + sum;
    }

    private double fac(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return fac(n - 1) * n;
        }
    }

    private double multK(double k, int count, boolean isMinus) {
        double mult = 1;
        for (int i = 0; i < count; i++) {
            if (isMinus) {
                mult = mult * (k - i);
            } else {
                mult = mult * (k + i);
            }
        }
        return mult;
    }

    private double findDiffForZero(int power) {
        double result = 0;
        for (int i = 1; i < power + 1; i++) {
            result += Math.pow(-1, i) * multK(power, i, true) * coords.get(power - i).getY() / fac(i);
        }
        return result + coords.get(power).getY();
    }

    private boolean isInputAtTheEnd(double input) {
        int index = -1;
        for (int i = 0; i < coords.size() - 1; i++) {
            if (coords.get(i).getX() <= input && input <= coords.get(i + 1).getX()) {
                index = i;
            }
        }
        if (index == -1) {
            return input >= coords.get(coords.size() - 1).getX();
        }
        return index >= coords.size() / 2;
    }


    private double interpolateNotEquallySpacedFunction(double input) {
        initDifferences(false);

        double sum = 0;
        for (int k = 1; k < coords.size(); k++) {
            double multiplication = 1;
            for (int j = 0; j < k; j++) {
                multiplication = multiplication * (input - coords.get(j).getX());
            }
            sum = sum + calcDivDifferences1(0, k) * multiplication;
        }
        return sum + coords.get(0).getY();
    }

    private void initDifferences(boolean isEquallySpaced) {
        divDifferences = new double[coords.size()][];
        for (int i = 0; i < coords.size(); i++) {
            divDifferences[i] = new double[coords.size() - i];
        }
        for (int i = 0; i < divDifferences.length; i++) {
            for (int j = 0; j < divDifferences[i].length; j++) {
                if (!isEquallySpaced) {
                    divDifferences[i][j] = calcDivDifferences1(i, i + j);
                } else {
                    divDifferences[i][j] = calcDivDifferences2(i, i + j);
                }
            }
        }
    }

    public double calcDivDifferences2(int indexStart, int indexEnd) {
        double answer;
        if (indexEnd == indexStart) {
            answer = coords.get(indexEnd).getY();
        } else {
            answer = calcDivDifferences2(indexStart + 1, indexEnd) - calcDivDifferences2(indexStart, indexEnd - 1);
        }
        return answer;
    }


    public double calcDivDifferences1(int indexStart, int indexEnd) {
        double answer;
        if (indexEnd == indexStart) {
            answer = coords.get(indexEnd).getY();
        } else {
            var first = calcDivDifferences1(indexStart + 1, indexEnd) - calcDivDifferences1(indexStart, indexEnd - 1);
            var second = coords.get(indexEnd).getX() - coords.get(indexStart).getX();
            answer = first / second;
            //answer = first;
        }
        return answer;
    }
}
