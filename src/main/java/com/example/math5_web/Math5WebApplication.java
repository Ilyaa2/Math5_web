package com.example.math5_web;

import com.example.math5_web.Coordinates.Coordinate;
import com.example.math5_web.InterpolationMethods.LagrangeMethod;
import com.example.math5_web.InterpolationMethods.NewtonMethod;
import com.example.math5_web.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

@SpringBootApplication
public class Math5WebApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        SpringApplication.run(Math5WebApplication.class, args);
        //System.out.println(new NewtonMethod().interpolate(0.47));
    }



}
