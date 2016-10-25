package application;

import algorithms.GraspNN;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inf109683 on 18.10.2016.
 */
public class MultipleLocalSearchExecution {

    private Graph graph;
    private ResultMultipleLocalSearch result;

    public MultipleLocalSearchExecution(Graph graph) {
        this.graph = graph;
        this.result = new ResultMultipleLocalSearch();
    }

    public void execute() {
        for (int i = 0; i < 10; i++) {
            SingleMultipleLocalSearchResult singleResult = executeSingleMultipleLocalSearch();
            updateResult(singleResult);
        }
    }

    private SingleMultipleLocalSearchResult executeSingleMultipleLocalSearch() {

        long startTime;
        long stopTime;
        double time;
        SingleMultipleLocalSearchResult singleResult = new SingleMultipleLocalSearchResult();
        startTime = System.nanoTime();
        for(int i = 0; i < 10; i++) {
            GraspNN graspGreedyCycle = new GraspNN(graph);
            graspGreedyCycle.execute();
            LocalSearchExecution localSearch = new LocalSearchExecution(graph);
            List<List<Integer>> allSolutions = graspGreedyCycle.getResult().getAllSolutions();
            List<Integer> allPathsCosts = graspGreedyCycle.getResult().getAllPathsCosts();
            for(int j = 0; j < allSolutions.size(); j++) {
                localSearch.execute(allSolutions.get(j), allPathsCosts.get(j));
                if (localSearch.getResult().getMinValue() < singleResult.getPathCost()) {
                    singleResult.setPathCost(localSearch.getResult().getMinValue());
                    singleResult.setBestSolution(localSearch.getResult().getBestSolution());
                }
            }
        }
        stopTime = System.nanoTime();
        time = (double)(stopTime - startTime)/ 1000000000 ;
        System.out.println("Time: " + time + " s");
        singleResult.setTime(time);
        return singleResult;
    }


    private void updateResult(SingleMultipleLocalSearchResult singleResult) {
        if(singleResult.getPathCost() < result.getMinValue()) {
            result.setMinValue(singleResult.getPathCost());
            result.setBestSolution(singleResult.getBestSolution());
        } else if(singleResult.getPathCost() > result.getMaxValue()) {
            result.setMaxValue(singleResult.getPathCost());
        }

        result.addToAvgValue(singleResult.getPathCost());
        result.addToTimes(singleResult.getTime());
    }

    public ResultMultipleLocalSearch getResult() {
        return result;
    }

    private class SingleMultipleLocalSearchResult {
        private int pathCost = Integer.MAX_VALUE;
        List<Integer> bestSolution = new ArrayList<>();
        double time;

        public int getPathCost() {
            return pathCost;
        }

        public void setPathCost(int pathCost) {
            this.pathCost = pathCost;
        }

        public List<Integer> getBestSolution() {
            return bestSolution;
        }

        public void setBestSolution(List<Integer> bestSolution) {
            this.bestSolution = bestSolution;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }
    }
}
