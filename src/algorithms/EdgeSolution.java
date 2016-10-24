package algorithms;

import application.Graph;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.*;

/**
 * Created by inf113149 on 11.10.2016.
 */
public class EdgeSolution {

    private Graph graph;
    private List<Integer> input;
    private Random random;

    private int maxGain = 0;
    private int resultFirstEdgeX;
    private int resultFirstEdgeY;
    private int resultSecondEdgeX;
    private int resultSecondEdgeY;
    private int singlePathCost;


    public EdgeSolution(Graph graph, List<Integer> input) {
        this.graph = graph;
        this.input = input;
        this.random = new Random();
    }

    public void execute() {
        //Random random = new Random();
        //int randIdx1 = random.nextInt(input.size());
        //int randIdx2 = random.nextInt(input.size());
        for (int i = 0; i < input.size() - 1; i++) {
            int firstEdgeX = input.get(i);
            int firstEdgeY = getVertexAfter(firstEdgeX, input);
            int firstEdgeLeft = getVertexBefore(firstEdgeX, input);
            int firstEdgeRight = getVertexAfter(firstEdgeY, input);
            for (int j = i + 1; j < input.size() - 1; j++) {
                int secondEdgeX = input.get(j);
                int secondEdgeY = getVertexAfter(secondEdgeX, input);
                int secondEdgeLeft = getVertexBefore(secondEdgeX, input);
                int secondEdgeRight = getVertexAfter(secondEdgeY, input);
                int cost = 0, newCost = 0;
                if (!areEdgesSeparated(firstEdgeX, firstEdgeY, secondEdgeX, secondEdgeY)) {
                    if (firstEdgeY == secondEdgeX) {
                        cost = getEdgeCost(firstEdgeLeft, firstEdgeX) +
                                getEdgeCost(firstEdgeX, firstEdgeY) +
                                getEdgeCost(secondEdgeX, secondEdgeY) +
                                getEdgeCost(secondEdgeY, secondEdgeRight);

                        newCost = getEdgeCost(firstEdgeLeft, secondEdgeY) +
                                getEdgeCost(secondEdgeY, secondEdgeX) +
                                getEdgeCost(firstEdgeY, firstEdgeX) +
                                getEdgeCost(firstEdgeX, secondEdgeRight);

                    } else if (secondEdgeY == firstEdgeX) {
                        cost = getEdgeCost(secondEdgeLeft, secondEdgeX) +
                                getEdgeCost(secondEdgeX, secondEdgeY) +
                                getEdgeCost(firstEdgeX, firstEdgeY) +
                                getEdgeCost(firstEdgeY, firstEdgeRight);

                        newCost = getEdgeCost(secondEdgeLeft, firstEdgeY) +
                                getEdgeCost(firstEdgeY, firstEdgeX) +
                                getEdgeCost(secondEdgeY, secondEdgeX) +
                                getEdgeCost(secondEdgeX, firstEdgeRight);

                    }
                } else if (firstEdgeRight == secondEdgeX) {
                    cost = getEdgeCost(firstEdgeLeft, firstEdgeX) +
                            getEdgeCost(firstEdgeY, secondEdgeX) +
                            getEdgeCost(secondEdgeY, secondEdgeRight);

                    newCost = getEdgeCost(firstEdgeLeft, secondEdgeX) +
                            getEdgeCost(secondEdgeY, firstEdgeX) +
                            getEdgeCost(firstEdgeY, secondEdgeRight);
                } else if (firstEdgeLeft == secondEdgeY) {
                    cost = getEdgeCost(secondEdgeLeft, secondEdgeX) +
                            getEdgeCost(secondEdgeY, firstEdgeX) +
                            getEdgeCost(firstEdgeY, firstEdgeRight);

                    newCost = getEdgeCost(secondEdgeLeft, firstEdgeX) +
                            getEdgeCost(firstEdgeY, secondEdgeX) +
                            getEdgeCost(secondEdgeY, firstEdgeRight);
                } else {
                    int firstEdgeCost = getEdgeCost(firstEdgeLeft, firstEdgeX) + getEdgeCost(firstEdgeY, firstEdgeRight);
                    int secondEdgeCost = getEdgeCost(secondEdgeLeft, secondEdgeX) + getEdgeCost(secondEdgeY, secondEdgeRight);
                    int newFirstEdgeCost = getEdgeCost(firstEdgeLeft, secondEdgeX) + getEdgeCost(secondEdgeY, firstEdgeRight);
                    int newSecondEdgeCost = getEdgeCost(secondEdgeLeft, firstEdgeX) + getEdgeCost(firstEdgeY, secondEdgeRight);
                    cost = firstEdgeCost + secondEdgeCost;
                    newCost = newFirstEdgeCost + newSecondEdgeCost;
                }
                if (cost > newCost) {
                    int gain = cost - newCost;
                    if (gain > maxGain) {
                        maxGain = gain;
                        resultFirstEdgeX = firstEdgeX;
                        resultFirstEdgeY = firstEdgeY;
                        resultSecondEdgeX = secondEdgeX;
                        resultSecondEdgeY = secondEdgeY;
                    }
                }
            }
        }
    }

    public List<Integer> executeRandomStep() {
        int firstVertexId = 0;
        while(firstVertexId == 0) {
            firstVertexId = random.nextInt(input.size()-2);
        }
        int secondVertexId = firstVertexId + 1;

        int thirdVertexId = getThirdRandomVertexId(firstVertexId);
        int fourthVertexId = thirdVertexId + 1;

        //swap
        Collections.swap(input, firstVertexId,  thirdVertexId);
        Collections.swap(input, secondVertexId, fourthVertexId);

        this.singlePathCost = EdgeSolution.calculatePathLength(graph, input);
        return input;
    }

    public int getThirdRandomVertexId(int firstVertexId) {
        int thirdVertexId = firstVertexId;

        while((thirdVertexId == firstVertexId
                || thirdVertexId == firstVertexId+1
                || thirdVertexId == firstVertexId-1)
                || thirdVertexId == 0
                || thirdVertexId == input.size()) {
            thirdVertexId = random.nextInt(input.size()-2);
        }
        return thirdVertexId;
    }

    public int getSinglePathCost() {
        return singlePathCost;
    }

    private boolean areEdgesSeparated(int firstEdgeX, int firstEdgeY, int secondEdgeX, int secondEdgeY) {
        Set<Integer> set = new HashSet<>();
        set.add(firstEdgeX);
        set.add(firstEdgeY);
        set.add(secondEdgeX);
        set.add(secondEdgeY);
        return set.size() == 4;
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
        if (resultFirstEdgeY == resultSecondEdgeX) {
            replaceElements(vertices, resultFirstEdgeX, resultSecondEdgeY);
        } else if (resultSecondEdgeY == resultFirstEdgeX) {
            replaceElements(vertices, resultSecondEdgeX, resultFirstEdgeY);
        } else {
            replaceElements(vertices, resultFirstEdgeX, resultSecondEdgeX);
            replaceElements(vertices, resultFirstEdgeY, resultSecondEdgeY);
        }
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
        resultFirstEdgeY = 0;
        resultSecondEdgeX = 0;
        resultSecondEdgeY = 0;
    }

    public static int calculatePathLength(Graph graph, List<Integer> pathVertices) {
        int pathLength = 0;
        for (int i = 0; i < pathVertices.size() - 1; i++) {
            pathLength += getEdgeCost(graph, pathVertices.get(i), pathVertices.get(i + 1));
        }
        return pathLength;
    }
}
