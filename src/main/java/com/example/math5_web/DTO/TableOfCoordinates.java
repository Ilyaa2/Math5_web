package com.example.math5_web.DTO;

public class TableOfCoordinates {
    private double[][] array;
    private double number;

    public double[][] getArray() {
        return array;
    }

    public TableOfCoordinates(){

    }

    public TableOfCoordinates(double[][] array, double number){
        this.array = array;
        this.number = number;
    }

    public void setArray(double[][] array) {
        this.array = array;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
