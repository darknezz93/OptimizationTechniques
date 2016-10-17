package application;

import algorithms.EdgeSolution;
import algorithms.VertexSwaping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 17.10.16.
 */
public class LocalSearchExecution {

    private ResultLocalSearch resultLocalSearch;

    private List<Integer> input;

    private Integer pathCost = 0;
    private Graph graph;

    LocalSearchExecution(Graph graph) {
        this.graph = graph;
        this.resultLocalSearch = new ResultLocalSearch();
    }

    private void fillResultLocalSearch(List<Integer> visitedVertexesIds, double bestTime) {
        resultLocalSearch.setBestTime(bestTime);
        if (resultLocalSearch.getMinValue() == 0 || resultLocalSearch.getMinValue() > pathCost) {
            resultLocalSearch.setMinValue(pathCost);
            resultLocalSearch.setBestSolution(visitedVertexesIds);
        } else if (resultLocalSearch.getMaxValue() < pathCost) {
            resultLocalSearch.setMaxValue(pathCost);
        }
        resultLocalSearch.addToAvgValue(pathCost);
    }

    public void execute(List<Integer> inputArray, int currentLength) {
        ArrayList<Integer> localSearchList = new ArrayList<>(inputArray);
        int edgeSwapMaxGain = 0, vertexSwapMaxGain = 0;
        long startTime;
        long endTime;
        double bestTime;
        do {
            EdgeSolution es = new EdgeSolution(graph, localSearchList);
            startTime = System.nanoTime();
            es.execute();
            VertexSwaping vertexSwaping = new VertexSwaping(graph, localSearchList);
            vertexSwaping.execute();
            endTime = System.nanoTime();

            edgeSwapMaxGain = es.getMaxGain();
            vertexSwapMaxGain = vertexSwaping.getMaxGain();
            //edgeSwapMaxGain = 0;
            if (edgeSwapMaxGain > vertexSwapMaxGain) {
                es.applyResult(localSearchList);
                currentLength -= edgeSwapMaxGain;
                //System.out.println("Edge: " + edgeSwapMaxGain +"\tVertex: " + vertexSwapMaxGain);
            } else if (vertexSwapMaxGain != 0) {
                vertexSwaping.applyResult(localSearchList);
                currentLength -= vertexSwapMaxGain;
                //System.out.println("Edge: " + edgeSwapMaxGain +"\tVertex: " + vertexSwapMaxGain);
            }
            bestTime = (double)(endTime - startTime)/ 100000 ;
        } while (edgeSwapMaxGain != 0 || vertexSwapMaxGain != 0);

        pathCost = currentLength;
        fillResultLocalSearch(localSearchList, bestTime);
    }

    public ResultLocalSearch getResult() {
        return resultLocalSearch;
    }


}
