package algorithms;

import application.Graph;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by inf113149 on 11.10.2016.
 */
public class EdgeSolution {

    private Graph graph;
    private List<Integer> input;

    private int maxGain = 0;
    private int resultFirstEdgeX;
    private int resultSecondEdgeX;


    public EdgeSolution(Graph graph, List<Integer> input) {
        this.graph = graph;
        this.input = input;
    }

    public void execute() {
        //Random random = new Random();
        //int randIdx1 = random.nextInt(input.size());
        //int randIdx2 = random.nextInt(input.size());
        for (int i = 0; i < input.size() - 1; i++) {
            int firstEdgeX = input.get(i);
            int firstEdgeY = getVertexAfter(firstEdgeX, input);
            int firstEdgeLeft = getVertexBefore(firstEdgeX, input);
            for (int j = i + 1; j < input.size() - 1; j++) {
                int secondEdgeX = input.get(j);
                int secondEdgeY = getVertexAfter(secondEdgeX, input);
                int cost = 0, newCost = 0;
                if (firstEdgeX == secondEdgeX || firstEdgeLeft == secondEdgeX || firstEdgeY == secondEdgeY) {
                    continue;
                } else {
                    int firstEdgeCost = getEdgeCost(firstEdgeX, firstEdgeY);
                    int secondEdgeCost = getEdgeCost(secondEdgeX, secondEdgeY);
                    int newFirstEdgeCost = getEdgeCost(firstEdgeX, secondEdgeX);
                    int newSecondEdgeCost = getEdgeCost(firstEdgeY, secondEdgeY);
                    cost = firstEdgeCost + secondEdgeCost;
                    newCost = newFirstEdgeCost + newSecondEdgeCost;
                }
                if (cost > newCost) {
                    int gain = cost - newCost;
                    if (gain > maxGain) {
                        maxGain = gain;
                        resultFirstEdgeX = firstEdgeX;
                        resultSecondEdgeX = secondEdgeX;
                    }
                }
            }
        }
    }

    private int getVertexAfter(int vertexId, List<Integer> input) {
        for (int i = 0; i < input.size() - 1; i++) {
            if (input.get(i) == vertexId) {
                return input.get(i + 1);
            }
        }
        throw new InvalidStateException("Selected vertex is ont on the list!");
    }

    private int getVertexBefore(int vertexId, List<Integer> input) {
        for (int i = 1; i < input.size(); i++) {
            if (input.get(i) == vertexId) {
                return input.get(i - 1);
            }
        }
        throw new InvalidStateException("Selected vertex is ont on the list!");
    }

    private Integer getEdgeCost(int vertexId1, int vertexId2) {
        return graph.getVertexes().get(vertexId1).findEdgeById(vertexId2).getCost();
    }

    private static Integer getEdgeCost(Graph graph, int vertexId1, int vertexId2) {
        return graph.getVertexes().get(vertexId1).findEdgeById(vertexId2).getCost();
    }

    public int getMaxGain() {
        return maxGain;
    }

    public void applyResult(List<Integer> vertices) {
        vertices.remove(vertices.size() - 1);
        //replaceElements(vertices, resultFirstEdgeX, resultSecondEdgeX);
        int reverseStartIndex, reverseEndIndex;
        if(vertices.indexOf(resultFirstEdgeX) < vertices.indexOf(resultSecondEdgeX)) {
            reverseStartIndex = vertices.indexOf(resultFirstEdgeX) + 1;
            reverseEndIndex = vertices.indexOf(resultSecondEdgeX) + 1;
        } else {
            reverseStartIndex = vertices.indexOf(resultSecondEdgeX) + 1;
            reverseEndIndex = vertices.indexOf(resultFirstEdgeX) + 1;
        }
        Collections.reverse(vertices.subList(reverseStartIndex, reverseEndIndex));
        vertices.add(vertices.get(0));
    }

    private void replaceElements(List<Integer> vertices, int first, int second) {
        int i = vertices.indexOf(first);
        int j = vertices.indexOf(second);
        vertices.set(i, second);
        vertices.set(j, first);
    }

    public void clearResult() {
        maxGain = 0;
        resultFirstEdgeX = 0;
        resultSecondEdgeX = 0;
    }

    public static int calculatePathLength(Graph graph, List<Integer> pathVertices) {
        int pathLength = 0;
        for (int i = 0; i < pathVertices.size() - 1; i++) {
            pathLength += getEdgeCost(graph, pathVertices.get(i), pathVertices.get(i + 1));
        }
        return pathLength;
    }
}
