package application;

import java.util.List;

/**
 * Created by adam on 17.10.16.
 */
public class LocalSearchExecution {

    private ResultLocalSearch resultLocalSearch;

    private List<Integer> input;

    private Integer pathCost = 0;

    LocalSearchExecution(List<Integer> input) {
        this.resultLocalSearch = new ResultLocalSearch();
        this.input = input;
    }

    private void fillResultLocalSearch(List<Integer> visitedVertexesIds) {
        if(resultLocalSearch.getMinValue() == 0 || resultLocalSearch.getMinValue() > pathCost) {
            resultLocalSearch.setMinValue(pathCost);
            resultLocalSearch.setBestSolution(visitedVertexesIds);
        } else if(resultLocalSearch.getMaxValue() < pathCost) {
            resultLocalSearch.setMaxValue(pathCost);
        }
        resultLocalSearch.addToAvgValue(pathCost);
    }

    public void execute() {
        long startTime = System.nanoTime();
        /**
         * TODO
         *
         *
         *
         *
         *
         *
         *
         */

        long endTime = System.nanoTime();
        resultLocalSearch.setTime((endTime - startTime) / 1000000);
    }




}
