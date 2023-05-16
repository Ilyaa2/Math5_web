package com.example.math5_web.util;

import com.example.math5_web.Coordinates.Coordinate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.UnaryOperator;

public class ChartGenerator {

    public static byte[] generateChartsUsingTable(List<Coordinate> coords, UnaryOperator<Double> newton, UnaryOperator<Double> lagrange, double input) throws IOException {
        XYSeries series1 = new XYSeries("base function");
        XYSeries series2 = new XYSeries("Newton's interpolation");
        XYSeries series3 = new XYSeries("Lagrange's interpolation");

        double min = coords.get(0).getX();
        double max = coords.get(0).getX();
        for (Coordinate c: coords){
            if (min > c.getX()) min = c.getX();
            if (max < c.getX()) max = c.getX();
        }

        for (double x = min - 0.5; x <= max + 0.5; x += 0.1){
            series2.add(x, newton.apply(x));
            series3.add(x, lagrange.apply(x));
        }

        for (Coordinate c: coords){
            series1.add(c.getX(), c.getY());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Functions",
                "X-Axis",
                "Y-Axis",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.RED);
        chart.getXYPlot().getRenderer().setSeriesPaint(1, Color.BLUE);
        chart.getXYPlot().getRenderer().setSeriesPaint(2, Color.MAGENTA);

        ChartRenderingInfo info = new ChartRenderingInfo();
        BufferedImage image = chart.createBufferedImage(1440, 900, info);

        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2));

        Rectangle2D dataArea = info.getPlotInfo().getDataArea();
        for (Coordinate c: coords) {
            int x = (int) chart.getXYPlot().getDomainAxis().valueToJava2D(c.getX(), dataArea, RectangleEdge.BOTTOM);
            int y = (int) chart.getXYPlot().getRangeAxis().valueToJava2D(c.getY(), dataArea, RectangleEdge.LEFT);
            g2.fillOval(x - 4, y - 4, 8, 8);
        }

        g2.setColor(Color.RED);
        int x = (int) chart.getXYPlot().getDomainAxis().valueToJava2D(input, dataArea, RectangleEdge.BOTTOM);
        int y = (int) chart.getXYPlot().getRangeAxis().valueToJava2D(newton.apply(input), dataArea, RectangleEdge.LEFT);
        g2.fillOval(x - 4, y - 4, 8, 8);

        g2.setColor(Color.MAGENTA);
        x = (int) chart.getXYPlot().getDomainAxis().valueToJava2D(input, dataArea, RectangleEdge.BOTTOM);
        y = (int) chart.getXYPlot().getRangeAxis().valueToJava2D(lagrange.apply(input), dataArea, RectangleEdge.LEFT);
        g2.fillOval(x - 4, y - 4, 8, 8);

        g2.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }
}
