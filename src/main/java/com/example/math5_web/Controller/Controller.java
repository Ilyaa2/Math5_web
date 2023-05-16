package com.example.math5_web.Controller;

import com.example.math5_web.Coordinates.Coordinate;
import com.example.math5_web.DTO.Interval;
import com.example.math5_web.DTO.TableOfCoordinates;
import com.example.math5_web.InterpolationMethods.LagrangeMethod;
import com.example.math5_web.InterpolationMethods.NewtonMethod;
import com.example.math5_web.util.ChartGenerator;
import com.example.math5_web.util.Parser;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.UnaryOperator;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    @GetMapping("/")
    public String home() {
        return "input.html";
    }

    @SneakyThrows
    @PostMapping("sendData")
    //Здесь используется механизм сессий для передачи результата из одного метода в другой,
    //это потокобезопасно, сессия у каждого пользователя своя
    public String sendData(@RequestBody TableOfCoordinates tableOfCoordinates, HttpSession session) {
        computationAndSaving(tableOfCoordinates, session);
        return "result.html";
    }

    private void computationAndSaving(TableOfCoordinates tableOfCoordinates, HttpSession session) {
        double input = tableOfCoordinates.getNumber();
        double[][] arr = tableOfCoordinates.getArray();
        ArrayList<Coordinate> coords = new ArrayList<>(arr.length);
        for (int i = 0; i < arr[0].length; i++) {
            coords.add(new Coordinate(arr[0][i], arr[1][i]));
        }

        Collections.sort(coords);
        var lagrange = new LagrangeMethod(coords);
        session.setAttribute("lagrange", lagrange.interpolate(input));
        session.setAttribute("functionL", lagrange.getFunction());
        var newton = new NewtonMethod(coords);
        var res = newton.interpolate(input);

        session.setAttribute("newton", res);
        session.setAttribute("differences", newton.getDivDifferences());
        session.setAttribute("coords", coords);
        session.setAttribute("input", input);
        session.setAttribute("functionN", newton.getFunction());
    }

    @GetMapping("/result")
    public String getResult(HttpSession session, Model model) {
        model.addAttribute("lagrange", session.getAttribute("lagrange"));
        model.addAttribute("newton", session.getAttribute("newton"));
        model.addAttribute("differences", session.getAttribute("differences"));
        return "result.html";
    }

    @GetMapping(value = "/Charts.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> newCharts(HttpSession session) throws IOException {
        UnaryOperator<Double> newton = (UnaryOperator<Double>) session.getAttribute("functionN");
        UnaryOperator<Double> lagrange = (UnaryOperator<Double>) session.getAttribute("functionL");
        ArrayList<Coordinate> coords = (ArrayList<Coordinate>) session.getAttribute("coords");
        Double input = (Double) session.getAttribute("input");
        return ResponseEntity.ok().body(ChartGenerator.generateChartsUsingTable(coords, newton, lagrange, input));
    }

    @SneakyThrows
    @GetMapping("/readFromFile")
    public String readFromFile(HttpSession session) {
        computationAndSaving(new Parser().parseFile(), session);
        return "result.html";
    }

    @PostMapping("function/{id}")
    public String calcFunction(@PathVariable("id") int id, @RequestBody Interval interval, HttpSession session) {
        UnaryOperator<Double> function;
        if (id == 1) {
            function = Math::sin;
        } else {
            //3x^3 - 7x^2 + 3
            function = (x) -> 3 * x * x * x - 7 * x * x + 3;
        }
        var coords = new Parser().transformFunctionToTable(function, interval);
        Collections.sort(coords);
        var lagrange = new LagrangeMethod(coords);
        session.setAttribute("lagrange", lagrange.interpolate(interval.getInput()));
        session.setAttribute("functionL", lagrange.getFunction());
        var newton = new NewtonMethod(coords);
        var res = newton.interpolate(interval.getInput());

        session.setAttribute("newton", res);
        session.setAttribute("differences", newton.getDivDifferences());
        session.setAttribute("coords", coords);
        session.setAttribute("input", interval.getInput());
        session.setAttribute("functionN", newton.getFunction());

        return "result.html";
    }

}
