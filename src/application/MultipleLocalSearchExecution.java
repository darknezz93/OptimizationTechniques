package application;

import algorithms.GraspGreedyCycle;
import algorithms.GraspNN;
import algorithms.NearestNeighbour;
import algorithms.NearestNeighbourRandom;

import java.util.List;

/**
 * Created by inf109683 on 18.10.2016.
 */
public class MultipleLocalSearchExecution {

    private Graph graph;
    private ResultMultipleLocalSearch result;
    public int pathCost = Integer.MAX_VALUE;

    public MultipleLocalSearchExecution(Graph graph) {
        this.graph = graph;
        this.result = new ResultMultipleLocalSearch();
    }


    public void execute() {
        /**
         * 10 x 1000 LocalSearch -> min, max, avg dla czasu + dlu sciezki
         * dla Grasp lub rand
         *
         */

        long startTime;
        long stopTime;
        double time;

        startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            GraspGreedyCycle graspNN = new GraspGreedyCycle(graph);
            graspNN.execute();
            System.out.println("Min: " + graspNN.getResult().getMinValue());
            LocalSearchExecution localSearch = new LocalSearchExecution(graph);
            List<List<Integer>> allSolutions = graspNN.getResult().getAllSolutions();
            List<Integer> allPathsCosts = graspNN.getResult().getAllPathsCosts();
            for (int j = 0; j < allSolutions.size(); j++) {
                localSearch.execute(allSolutions.get(j), allPathsCosts.get(j));
                if (localSearch.getResult().getMinValue() < pathCost) {
                    pathCost = localSearch.getResult().getMinValue();
                    fillResult(localSearch.getResult().getBestSolution());
                }
            }
        }
        stopTime = System.nanoTime();
        time = (double)(stopTime - startTime)/ 1000000000 ;
        System.out.println("Time: " + time + " s");
        result.setBestTime(time);
    }

    private void fillResult(List<Integer> solution) {
        if(result.getMinValue() == 0 || result.getMinValue() < pathCost) {
            result.setMinValue(pathCost);
            result.setBestSolution(solution);
        } else if(result.getMaxValue() < pathCost) {
            result.setMaxValue(pathCost);
        }
        result.addToAvgValue(pathCost);
    }
}
