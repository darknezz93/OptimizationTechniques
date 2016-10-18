package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inf109683 on 18.10.2016.
 */
public class ResultMultipleLocalSearch {

    private int minValue;
    private int avgValue;
    private int maxValue;
    private double bestTime;
    private double avgTime;
    private double maxTime;

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
        return avgValue/100;
    }

    public void setAvgValue(int avgValue) {
        this.avgValue = avgValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void addToAvgValue(Integer value) {
        avgValue += value;
    }

    public double getBestTime() {
        return bestTime;
    }

    public void setBestTime(double bestTime) {
        this.bestTime = bestTime;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }
}
