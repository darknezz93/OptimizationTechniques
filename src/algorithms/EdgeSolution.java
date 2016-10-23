package algorithms;

import application.Graph;

import java.util.List;

/**
 * Created by inf113149 on 11.10.2016.
 */
public class EdgeSolution {

    private Graph graph;
    private List<Integer> input;

    private int maxGain = 0;
    private int sourceEdgeIndex;
    private int newEdgeIndex;


    public EdgeSolution(Graph graph, List<Integer> input) {
        this.graph = graph;
        this.input = input;
    }

    public void execute() {

        for (int i = 0; i < input.size() - 1; i++) {
            int firstLeftIdx = i == 0 ? input.size() - 2 : i - 1;
            int firstRightIdx = i == input.size() - 2 ? 1 : i + 2;
            Integer firstLeftEdgeCost = getEdgeCost(input.get(firstLeftIdx), input.get(i));
            Integer firstRightEdgeCost = getEdgeCost(input.get(i + 1), input.get(firstRightIdx));
            for (int j = i + 2; j < input.size() - 1; j++) {
                try {
                    if (Math.abs(i - j) > 2) {
                        int secondLeftIdx = j == 0 ? input.size() - 2 : j - 1;
                        int secondRightIdx = j == input.size() - 2 ? 1 : j + 2;
                        if((i==0) && (secondRightIdx ==50)){continue;}
                        if((i==1) && (secondRightIdx ==1)){continue;}
                        Integer secondLeftEdgeCost = getEdgeCost(input.get(secondLeftIdx), input.get(j));
                        Integer secondRightEdgeCost = getEdgeCost(input.get(j + 1), input.get(secondRightIdx));

                        Integer newFirstLeftEdgeCost = getEdgeCost(input.get(firstLeftIdx), input.get(j));
                        Integer newFirstRightEdgeCost = getEdgeCost(input.get(j + 1), input.get(firstRightIdx));
                        Integer newSecondLeftEdgeCost = getEdgeCost(input.get(secondLeftIdx), input.get(i));
                        Integer newSecondRightEdgeCost = getEdgeCost(input.get(i + 1), input.get(secondRightIdx));
                        if (firstLeftEdgeCost + firstRightEdgeCost + secondLeftEdgeCost + secondRightEdgeCost >
                                newFirstLeftEdgeCost + newFirstRightEdgeCost + newSecondLeftEdgeCost + newSecondRightEdgeCost) {
                            int gain = firstLeftEdgeCost + firstRightEdgeCost + secondLeftEdgeCost + secondRightEdgeCost
                                    - (newFirstLeftEdgeCost + newFirstRightEdgeCost + newSecondLeftEdgeCost + newSecondRightEdgeCost);
                            if (gain > maxGain) {
                                maxGain = gain;
                                sourceEdgeIndex = i;
                                newEdgeIndex = j;
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }
    }

    private Integer getEdgeCost(int vertexId1, int vertexId2) {
        return graph.getVertexes().get(vertexId1).findEdgeById(vertexId2).getCost();
    }

    public int getMaxGain() {
        return maxGain;
    }

    public void applyResult(List<Integer> vertices) {
        int tempX = vertices.get(sourceEdgeIndex);
        int tempY = vertices.get(sourceEdgeIndex + 1);

        vertices.set(sourceEdgeIndex, vertices.get(newEdgeIndex));
        if (sourceEdgeIndex == 0) {
            vertices.set(vertices.size() - 1, vertices.get(newEdgeIndex));
        }
        vertices.set(sourceEdgeIndex + 1, vertices.get(newEdgeIndex + 1));
        if (sourceEdgeIndex == vertices.size() - 2) {
            vertices.set(0, vertices.get(newEdgeIndex));
        }
        vertices.set(newEdgeIndex, tempX);
        if (newEdgeIndex == 0) {
            vertices.set(vertices.size() - 1, tempX);
        }
        vertices.set(newEdgeIndex + 1, tempY);
        if (newEdgeIndex == vertices.size() - 2) {
            vertices.set(0, tempY);
        }
    }

    public void clearResult() {
        maxGain = 0;
        sourceEdgeIndex = 0;
        newEdgeIndex = 0;
    }
}
