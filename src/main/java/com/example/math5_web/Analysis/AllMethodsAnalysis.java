package com.example.math5_web.Analysis;

import com.example.math5_web.Coordinates.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class AllMethodsAnalysis {
    public List<Coordinate> coords;

    public AllMethodsAnalysis(List<Coordinate> coords){
        this.coords = coords;
    }

    public void initCoords(){
        coords = new ArrayList<>() {{
            add(new Coordinate(0.15, 1.25));
            add(new Coordinate(0.2, 2.38));
            add(new Coordinate(0.33, 3.79));
            add(new Coordinate(0.47, 5.44));
            add(new Coordinate(0.62, 7.14));
        }};
    }


}
