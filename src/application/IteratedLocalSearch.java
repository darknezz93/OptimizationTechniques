package application;

import algorithms.GraspNN;
import algorithms.VertexSwaping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by adam on 22.10.16.
 */
public class IteratedLocalSearch {

    private Graph graph;
    private Random random;
    private ResultIteratedLocalSearch result;
    private double timeBarrier;
    private static final int numOfPerturbances = 3;

    public IteratedLocalSearch(Graph graph, double avgMultipleLocalSearchTime) {
        this.graph = graph;
        this.random = new Random();
        this.result = new ResultIteratedLocalSearch();
        this.timeBarrier = avgMultipleLocalSearchTime;
    }

    public ResultIteratedLocalSearch getResult() {
        return this.result;
    }

    public void execute() {

        GraspNN graspNN = new GraspNN(graph);
        graspNN.execute();

        for (int i = 0; i < 10; i++) {

            double startTime = (double)System.nanoTime() / 1000000000;

            LocalSearchExecution localSearch = new LocalSearchExecution(graph);
            localSearch.execute(graspNN.getResult().getAllSolutions().get(i), graspNN.getResult().getAllPathsCosts().get(i));

            List<Integer> lsSolution = localSearch.getResult().getBestSolution();
            int lsCost = localSearch.getResult().getMinValue();

            SingleIteratedLocalSearchResult singleResult = executeSingleIteratedLocalSearch(lsSolution, lsCost, startTime);
            updateResult(singleResult);
        }
    }

    private SingleIteratedLocalSearchResult executeSingleIteratedLocalSearch(List<Integer> lsSolution, int lsCost, double startTime) {

        SingleIteratedLocalSearchResult singleResult = new SingleIteratedLocalSearchResult();
        LocalSearchExecution localSearch = new LocalSearchExecution(graph);
        singleResult.setBestSolution(lsSolution);
        singleResult.setPathCost(lsCost);

        while(checkTime(startTime)) {

            Perturbation perturbation = new Perturbation(singleResult.getBestSolution(), singleResult.getPathCost());

            localSearch.execute(perturbation.getSolution(), perturbation.getPathCost());

            if(localSearch.getResult().getMinValue() < singleResult.getPathCost() && localSearch.getResult().getMinValue() != 0) {
                singleResult.setBestSolution(localSearch.getResult().getBestSolution());
                singleResult.setPathCost(localSearch.getResult().getMinValue());
            }
        }
        return singleResult;
    }


    private boolean checkTime(double startTime) {
        if(((double)System.nanoTime() / 1000000000) - startTime <= timeBarrier) {
            return true;
        }
        return false;
    }

    private void updateResult(SingleIteratedLocalSearchResult singleResult) {
        if(singleResult.getPathCost() < result.getMinValue()) {
            result.setMinValue(singleResult.getPathCost());
            result.setBestSolution(singleResult.getBestSolution());
        } else if(singleResult.getPathCost() > result.getMaxValue()) {
            result.setMaxValue(singleResult.getPathCost());
        }
        result.addToAvgValue(singleResult.getPathCost());
    }

    private class Perturbation {
        private int pathCost;
        private List<Integer> solution = new ArrayList<>();

        public Perturbation(List<Integer> currentSolution, int currentCost) {
            performPerturbation(currentSolution, currentCost);
        }

        private void performPerturbation(List<Integer> currentSolution, int currentCost) {

            for(int i = 0; i < numOfPerturbances; i++) {
                if(random.nextBoolean()) {
                    VertexSwaping vertexSwaping = new VertexSwaping(graph, currentSolution);
                    solution = vertexSwaping.executeRandomStep();
                    pathCost -= vertexSwaping.getMaxGain();

                } else {
                    VertexSwaping vertexSwaping = new VertexSwaping(graph, currentSolution);
                    solution = vertexSwaping.executeRandomStep();
                    pathCost -= vertexSwaping.getMaxGain();
                }
                currentSolution = solution;
            }
        }

        public int getPathCost() {
            return pathCost;
        }

        public List<Integer> getSolution() {
            return solution;
        }
    }


    private class SingleIteratedLocalSearchResult {
        private int pathCost = Integer.MAX_VALUE;
        List<Integer> bestSolution = new ArrayList<>();

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

    }
}
