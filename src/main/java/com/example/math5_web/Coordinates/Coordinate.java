package com.example.math5_web.Coordinates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinate implements Comparable<Coordinate>{
    private double x;
    private double y;

    public Coordinate() {
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Coordinate o) {
        if (o.getX() > this.x){
            return -1;
        } else {
            return 1;
        }
    }
}
