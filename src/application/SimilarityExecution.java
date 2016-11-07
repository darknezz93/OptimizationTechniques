package application;

import algorithms.RandomNeighbour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by inf113149 on 25.10.2016.
 */
public class SimilarityExecution {

    private Graph graph;

    public SimilarityExecution(Graph graph) {
        this.graph = graph;
    }

    public void execute() {
        List<List<Integer>> solutions = new ArrayList<>();
        List<Integer> pathCosts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RandomNeighbour randomNeighbour = new RandomNeighbour(graph);
            randomNeighbour.execute();
            List<List<Integer>> allSolutions = randomNeighbour.getResult().getAllSolutions();
            List<Integer> allPathsCosts = randomNeighbour.getResult().getAllPathsCosts();
            for (int j = 0; j < allSolutions.size(); j++) {
                LocalSearchExecution localSearchExecution = new LocalSearchExecution(graph);
                localSearchExecution.execute(allSolutions.get(j), allPathsCosts.get(j));
                solutions.add(localSearchExecution.getResult().getBestSolution());
                pathCosts.add(localSearchExecution.getResult().getMinValue());
            }
        }

        int position = pathCosts.indexOf(Collections.min(pathCosts));
        List<Integer> bestSolution = solutions.get(position);

        //VERTEX SIMILARITY ALL
        /*List<PlotNode> vertexSimilarityAll = new ArrayList<>();
        for (int i = 0; i < solutions.size(); i++) {
            float averageVertexSimilarity = getAverageVertexSimilarity(solutions.get(i), solutions);
            vertexSimilarityAll.add(new PlotNode(pathCosts.get(i), averageVertexSimilarity));
        }
        parsePlotNodes(vertexSimilarityAll);*/

        //VERTEX SIMILARITY BEST
       /* List<PlotNode> vertexSimilarityBest = new ArrayList<>();
        for (int i = 0; i < solutions.size(); i++) {
            float similarity = getSimilarVerticesNumber(bestSolution, solutions.get(i));
            vertexSimilarityBest.add(new PlotNode(pathCosts.get(i), similarity));
        }
        parsePlotNodes(vertexSimilarityBest);*/



        //EDGE SIMILARITY ALL
       /* List<PlotNode> edgeSimilarityAll = new ArrayList<>();
        for (int i = 0; i < solutions.size(); i++) {
            float averageEdgeSimilarity = getAverageEdgeSimilarity(solutions.get(i), solutions);
            edgeSimilarityAll.add(new PlotNode(pathCosts.get(i), averageEdgeSimilarity));
        }*/
        //parsePlotNodes(edgeSimilarityAll);

        //EDGE SIMILARITY BEST
        List<PlotNode> edgeSimilarityBest = new ArrayList<>();
        for (int i = 0; i < solutions.size(); i++) {
            float similarity = getSimilarEdgesNumber(bestSolution, solutions.get(i));
            edgeSimilarityBest.add(new PlotNode(pathCosts.get(i), similarity));
        }
        parsePlotNodes(edgeSimilarityBest);

    }

    public float getAverageVertexSimilarity(List<Integer> solution, List<List<Integer>> allSolutions) {
        int count = 0;
        int solutionIndex = allSolutions.indexOf(solution);
        for (int i = 0; i < allSolutions.size(); i++) {
            if (i != solutionIndex) {
                count += getSimilarVerticesNumber(solution, allSolutions.get(i));
            }
        }
        return (float) count / (allSolutions.size() - 1);
    }

    public float getAverageEdgeSimilarity(List<Integer> solution, List<List<Integer>> allSolutions) {
        int count = 0;
        int solutionIndex = allSolutions.indexOf(solution);
        for (int i = 0; i < allSolutions.size(); i++) {
            if (i != solutionIndex) {
                count += getSimilarEdgesNumber(solution, allSolutions.get(i));
            }
        }
        return (float) count / (allSolutions.size() - 1);
    }

    public int getSimilarVerticesNumber(List<Integer> firstList, List<Integer> secondList) {
        int similarVerticesCount = 0;
        for (int i = 0; i < firstList.size() - 1; i++) {
            if (secondList.contains(firstList.get(i))) {
                similarVerticesCount++;
            }
        }
        return similarVerticesCount;
    }

    public int getSimilarEdgesNumber(List<Integer> firstList, List<Integer> secondList) {
        int similarEdgesCount = 0;
        for (int i = 0; i < firstList.size() - 1; i++) {
            int firstEdgeX = firstList.get(i);
            int firstEdgeY = firstList.get(i + 1);
            for (int j = 0; j < secondList.size() - 1; j++) {
                int secondEdgeX = secondList.get(j);
                int secondEdgeY = secondList.get(j + 1);
                if (firstEdgeX == secondEdgeX && firstEdgeY == secondEdgeY) {
                    similarEdgesCount++;
                }
            }
        }
        return similarEdgesCount;
    }

    public void parsePlotNodes(List<PlotNode> nodes) {
        for (PlotNode node : nodes) {
            System.out.println("" + node.getPathCost() + "\t" + node.getValue());
        }
    }

    public static class PlotNode {
        private int pathCost;
        private float value;

        public PlotNode(int pathCost, float value) {
            this.pathCost = pathCost;
            this.value = value;
        }

        public int getPathCost() {
            return pathCost;
        }

        public float getValue() {
            return value;
        }
    }
}
