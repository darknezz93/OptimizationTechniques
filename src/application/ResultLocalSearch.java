package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 17.10.16.
 */
public class ResultLocalSearch {

    private int minValue;
    private int avgValue;
    private int maxValue;

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
        return avgValue;
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
}
