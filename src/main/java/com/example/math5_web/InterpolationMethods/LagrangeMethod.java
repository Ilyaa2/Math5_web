package com.example.math5_web.InterpolationMethods;

import com.example.math5_web.Coordinates.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class LagrangeMethod extends InterpolationMethod{
    private UnaryOperator<Double> function;

    public LagrangeMethod(List<Coordinate> coords) {
        super(coords);
    }

    public LagrangeMethod(){
        super();
    }

    @Override
    public double interpolate(double input) {
        double result = 0;
        for (int i = 0; i < coords.size(); i++) {
            double multiplication = 1;
            for (int j = 0; j < coords.size(); j++) {
                if (i == j) continue;
                double tmp = (input - coords.get(j).getX()) / (coords.get(i).getX() - coords.get(j).getX());
                multiplication = multiplication * tmp;
            }
            result = result + multiplication * coords.get(i).getY();
        }
        return result;
    }

    public UnaryOperator<Double> getFunction(){
        return this::interpolate;
    }
}
