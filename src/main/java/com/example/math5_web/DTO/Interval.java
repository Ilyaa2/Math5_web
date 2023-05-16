package com.example.math5_web.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interval {
    private double left;
    private double right;
    private int count;
    private double input;

    public Interval(){

    }

    public Interval(double left, double right, int count, double input){
        this.input = input;
        this.left = left;
        this.count = count;
        this.right = right;
    }

}
