package application;

import algorithms.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 04.10.2016.
 */
public class Main {

    public static void main(String[] args) throws JAXBException {
        AlgorithmService as = new AlgorithmService();
        Graph graph = as.getGraphFromFile("data" + File.separator + "kroA100.xml");

        NearestNeighbour nn = new NearestNeighbour(graph);
        nn.execute();
        System.out.println("NN: ");
        System.out.println("Min: " + nn.getResult().getMinValue());
        System.out.println("Max: " + nn.getResult().getMaxValue());
        System.out.println("Avg: " + nn.getResult().getAvgValue());



        ArrayList<Integer> localSearchList = new ArrayList<>(nn.getResult().getAllSolutions().get(30));
        int currentLength = nn.getResult().getAllPathsCosts().get(30);
        int edgeSwapMaxGain = 0, vertexSwapMaxGain = 0;
        do {
            System.out.print("Path: ");
            for (Integer node : localSearchList) {
                System.out.print(node + ", ");
            }
            System.out.println();
            EdgeSolution es = new EdgeSolution(graph, localSearchList);
            es.execute();
            VertexSwaping vertexSwaping = new VertexSwaping(graph, localSearchList);
            vertexSwaping.execute();

            edgeSwapMaxGain = es.getMaxGain();
            vertexSwapMaxGain = vertexSwaping.getMaxGain();
            System.out.println("Edge: " + edgeSwapMaxGain + "\tVertex:" + vertexSwapMaxGain);
            if (edgeSwapMaxGain > vertexSwapMaxGain) {
                es.applyResult(localSearchList);
                currentLength -= edgeSwapMaxGain;
            } else if (vertexSwapMaxGain != 0) {
                vertexSwaping.applyResult(localSearchList);
                currentLength -= vertexSwapMaxGain;
            }
        } while (edgeSwapMaxGain != 0 || vertexSwapMaxGain != 0);

        System.out.println("Final length: " + currentLength);
/*
        NearestNeighbourRandom nnRandom = new NearestNeighbourRandom(graph);
        nnRandom.execute();
        System.out.println("\n\nNN_Random: ");
        System.out.println("Min: " + nnRandom.getResult().getMinValue());
        System.out.println("Max: " + nnRandom.getResult().getMaxValue());
        System.out.println("Avg: " + nnRandom.getResult().getAvgValue());

        System.out.print("Path: ");
        for(Integer node : nnRandom.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }

        GraspNN graspNN = new GraspNN(graph);
        graspNN.execute();
        System.out.println("\n\nGraspNN: ");
        System.out.println("Min: " + graspNN.getResult().getMinValue());
        System.out.println("Max: " + graspNN.getResult().getMaxValue());
        System.out.println("Avg: " + graspNN.getResult().getAvgValue());

        System.out.print("Path: ");
        for(Integer node : graspNN.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }


        GreedyCycle gc = new GreedyCycle(graph);
        gc.execute();
        System.out.println("\n\nGreedyCycle: ");
        System.out.println("Min: " + gc.getResult().getMinValue());
        System.out.println("Max: " + gc.getResult().getMaxValue());
        System.out.println("Avg: " + gc.getResult().getAvgValue());

        System.out.print("Path: ");
        for(Integer node : gc.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }

        GraspGreedyCycle graspGreedyCycle = new GraspGreedyCycle(graph);
        graspGreedyCycle.execute();
        System.out.println("\n\nGraspGreedyCycle: ");
        System.out.println("Min: " + graspGreedyCycle.getResult().getMinValue());
        System.out.println("Max: " + graspGreedyCycle.getResult().getMaxValue());
        System.out.println("Avg: " + graspGreedyCycle.getResult().getAvgValue());

        System.out.print("Path: ");
        for(Integer node : graspGreedyCycle.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }*/

    }

    public static void parseEdges(List<Integer> result) {
        String s = "\n";
        int edgeId = 0;
        for (int i = 0; i < result.size() - 1; i++) {
            int secIdx = i == result.size() - 1 ? 0 : i + 1;
            s += "<edge id=\"" + edgeId + "\" source=\"" + (result.get(i) + 1) + "\" target=\"" + (result.get(secIdx) + 1) + "\" />";
            edgeId++;
        }
        System.out.println(s);
    }
}
