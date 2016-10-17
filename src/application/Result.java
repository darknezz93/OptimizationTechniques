package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 08.10.16.
 */
public class Result {

    private int minValue;
    private int avgValue;
    private int maxValue;

    private List<Integer> bestSolution = new ArrayList<>();

    private List<List<Integer>> allSolutions = new ArrayList<>();

    private List<Integer> allPathsCosts = new ArrayList<>();

    public List<Integer> getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(List<Integer> bestSolution) {
        this.bestSolution = bestSolution;
    }

    public List<List<Integer>> getAllSolutions() {
        return allSolutions;
    }

    public void addToAllSolutions(List<Integer> simpleSolution) {
        this.allSolutions.add(simpleSolution);
    }

    public List<Integer> getAllPathsCosts() {
        return allPathsCosts;
    }

    public void addToAllPathsCosts(Integer simpleCost) {
        this.allPathsCosts.add(simpleCost);
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
