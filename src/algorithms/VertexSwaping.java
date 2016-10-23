package algorithms;

import application.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kamil on 2016-10-16.
 */
public class VertexSwaping {

    private final List<Integer> unusedVertices;
    private Graph graph;
    private List<Integer> input;
    private Random random;

    private int maxGain;
    private int sourceVertexIndex;
    private int newVertexIndex;


    public VertexSwaping(Graph graph, List<Integer> input) {
        this.graph = graph;
        this.input = input;
        this.random = new Random();
        this.unusedVertices = createUnusedVerticesList(input);
    }

    private List<Integer> createUnusedVerticesList(List<Integer> input) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < graph.getVertexes().size(); i++) {
            if (!input.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public void execute() {
        for (int i = 0; i < input.size() - 1; i++) {
            //Integer firstEdgeCost = getEdgeCost(input.get(i), input.get(i + 1));
            int leftVertexIndex = i == 0 ? input.size() - 2 : i - 1;
            int rightVertexIndex = i == input.size() - 2 ? 0 : i + 1;
            Integer leftEdgeCost = getEdgeCost(input.get(leftVertexIndex), input.get(i));
            Integer rightEdgeCost = getEdgeCost(input.get(i), input.get(rightVertexIndex));

            for (int j = 0; j < unusedVertices.size(); j++) {
                Integer newLeftEdgeCost = getEdgeCost(input.get(leftVertexIndex), unusedVertices.get(j));
                Integer newRightEdgeCost = getEdgeCost(unusedVertices.get(j), input.get(rightVertexIndex));

                if (leftEdgeCost + rightEdgeCost > newLeftEdgeCost + newRightEdgeCost) {
                    int gain = leftEdgeCost + rightEdgeCost - (newLeftEdgeCost + newRightEdgeCost);
                    if (gain > maxGain) {
                        maxGain = gain;
                        sourceVertexIndex = i;
                        newVertexIndex = j;
                    }
                }
            }
        }
    }

    public List<Integer> executeRandomStep() {
        int i = random.nextInt(input.size()-1);
        int leftVertexIndex = i == 0 ? input.size() - 2 : i - 1;
        int rightVertexIndex = i == input.size() - 2 ? 0 : i + 1;
        Integer leftEdgeCost = getEdgeCost(input.get(leftVertexIndex), input.get(i));
        Integer rightEdgeCost = getEdgeCost(input.get(i), input.get(rightVertexIndex));

        for (int j = 0; j < unusedVertices.size(); j++) {
            Integer newLeftEdgeCost = getEdgeCost(input.get(leftVertexIndex), unusedVertices.get(j));
            Integer newRightEdgeCost = getEdgeCost(unusedVertices.get(j), input.get(rightVertexIndex));

            if (leftEdgeCost + rightEdgeCost > newLeftEdgeCost + newRightEdgeCost) {
                int gain = leftEdgeCost + rightEdgeCost - (newLeftEdgeCost + newRightEdgeCost);
                if (gain > maxGain) {
                    maxGain = gain;
                    sourceVertexIndex = i;
                    newVertexIndex = j;
                }
            }
        }
        if(maxGain != 0) {
            applyResult(input);
        }
        return input;
    }

    private Integer getEdgeCost(int vertexId1, int vertexId2) {
        return graph.getVertexes().get(vertexId1).findEdgeById(vertexId2).getCost();
    }

    public int getMaxGain() {
        return maxGain;
    }

    public void applyResult(List<Integer> vertices) {
        if (sourceVertexIndex == 0 || sourceVertexIndex == vertices.size() - 1) {
            vertices.set(0, unusedVertices.get(newVertexIndex));
            vertices.set(vertices.size() - 1, unusedVertices.get(newVertexIndex));
        } else {
            vertices.set(sourceVertexIndex, unusedVertices.get(newVertexIndex));
        }
    }

    public void clearResult() {
        maxGain = 0;
        newVertexIndex = 0;
        sourceVertexIndex = 0;
    }
}
