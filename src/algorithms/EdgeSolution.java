package algorithms;

import application.Graph;
import application.Vertex;

import java.util.List;
import java.util.Random;

/**
 * Created by inf113149 on 11.10.2016.
 */
public class EdgeSolution {

    private Graph graph;
    private List<Integer> input;

    private int maxGain = 0;
    private int firstEdge;
    private int secondEdge;


    public EdgeSolution(Graph graph, List<Integer> input) {
        this.graph = graph;
        this.input = input;
    }

    public void execute() {
        //Random random = new Random();
        //int randIdx1 = random.nextInt(input.size());
        //int randIdx2 = random.nextInt(input.size());

        for (int i = 0; i < input.size() - 1; i++) {
            Integer firstEdgeCost = getEdgeCost(input.get(i), input.get(i + 1));
            for (int j = 0; j < input.size() - 1; j++) {
                try {
                    Integer secondEdgeCost = getEdgeCost(input.get(j), input.get(j + 1));
                    Integer newFirstEdgeCost = getEdgeCost(input.get(i), input.get(j + 1));
                    Integer newSecondEdgeCost = getEdgeCost(input.get(j), input.get(i + 1));
                    if (firstEdgeCost + secondEdgeCost > newFirstEdgeCost + newSecondEdgeCost) {
                        int gain = firstEdgeCost + secondEdgeCost - (newFirstEdgeCost + newSecondEdgeCost);
                        if (gain > maxGain) {
                            maxGain = gain;
                            firstEdge = i;
                            secondEdge = j;
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
}
