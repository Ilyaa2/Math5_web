package com.example.math5_web.util;

import com.example.math5_web.Coordinates.Coordinate;
import com.example.math5_web.DTO.Interval;
import com.example.math5_web.DTO.TableOfCoordinates;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.UnaryOperator;


public class Parser {

    public TableOfCoordinates parseFile() throws IOException, URISyntaxException {
        var uri = getClass().getResource(String.format("/%s", "static/input.txt")).toURI();
        var fileReader = new Scanner(new FileReader(Paths.get(uri).toFile()));
        int count = 0;
        while (fileReader.hasNext()){
            fileReader.nextLine();
            count++;
        }
        fileReader = new Scanner(new FileReader(Paths.get(uri).toFile()));
        double[][] array = new double[2][count-1];
        double input = 0;
        int i=0;
        while (fileReader.hasNext()){
            if (i != count - 1) {
                var s = fileReader.nextLine().split(" ");
                array[0][i] = Double.parseDouble(s[0]);
                array[1][i] = Double.parseDouble(s[1]);
            } else {
                input = Double.parseDouble(fileReader.nextLine());
            }
            i++;
        }
        return new TableOfCoordinates(array, input);
    }


    public ArrayList<Coordinate> transformFunctionToTable(UnaryOperator<Double> function, Interval interval){
        ArrayList<Coordinate> list = new ArrayList<>();
        int c =0;
        double step = 0;
        while (interval.getCount() > c){
            step += (interval.getRight() - interval.getLeft()) / interval.getCount();
            list.add(new Coordinate(interval.getLeft() + step, function.apply(interval.getLeft() + step)));
            c++;
        }
        return list;
    }
}
