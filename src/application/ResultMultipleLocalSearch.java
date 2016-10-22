package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by inf109683 on 18.10.2016.
 */
public class ResultMultipleLocalSearch {

    private int minValue = Integer.MAX_VALUE;
    private int avgValue;
    private int maxValue;
    private List<Double> times = new ArrayList<>();

    private List<Integer> bestSolution = new ArrayList<>();

    public List<Integer> getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(List<Integer> bestSolution) {
        this.bestSolution = bestSolution;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getAvgValue() {
        return avgValue/10;
    }

    public void addToAvgValue(int value) {
        this.avgValue += value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void addToTimes(Double value) {
        times.add(value);
    }

    public double getBestTime() {
        return Collections.min(times);
    }

    public double getAvgTime() {
        return times.stream().mapToDouble(val -> val).average().getAsDouble();
    }

    public double getMaxTime() {
        return Collections.max(times);
    }

}
