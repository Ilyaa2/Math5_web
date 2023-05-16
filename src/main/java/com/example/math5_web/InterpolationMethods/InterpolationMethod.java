package com.example.math5_web.InterpolationMethods;

import com.example.math5_web.Coordinates.Coordinate;

import java.util.ArrayList;
import java.util.List;

public abstract class InterpolationMethod {
    protected List<Coordinate> coords;

    public InterpolationMethod(List<Coordinate> coords){
        this.coords = coords;
    }

    public InterpolationMethod(){
        initCoords();
    }

    private void initCoords(){
        /*
        coords = new ArrayList<>() {{
            add(new Coordinate(0.15, 1.25));
            add(new Coordinate(0.2, 2.38));
            add(new Coordinate(0.33, 3.79));
            add(new Coordinate(0.47, 5.44));
            add(new Coordinate(0.62, 7.14));
        }};

         */

        coords = new ArrayList<>() {{
            add(new Coordinate(0.1, 1.25));
            add(new Coordinate(0.2, 2.38));
            add(new Coordinate(0.3, 3.79));
            add(new Coordinate(0.4, 5.44));
            add(new Coordinate(0.5, 7.14));
        }};


        /*
        coords = new ArrayList<>() {{
            add(new Coordinate(0.15, 1.25));
            add(new Coordinate(0.2, 2.38));
            add(new Coordinate(0.33, 3.79));
            add(new Coordinate(0.47, 5.44));
            add(new Coordinate(0.62, 7.14));
        }};

         */
    }

    public abstract double interpolate(double input);
}
