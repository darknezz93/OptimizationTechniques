package application;

import algorithms.GraspGreedyCycle;
import algorithms.GraspNN;
import algorithms.GreedyCycle;
import algorithms.NearestNeighbour;

import javax.xml.bind.JAXBException;
import java.io.File;
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

        System.out.print("Path: ");
        for (Integer node : nn.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }

        LocalSearchExecution localSearch = new LocalSearchExecution(graph);
        for (int i = 0; i < nn.getResult().getAllSolutions().size(); i++) {
            localSearch.execute(nn.getResult().getAllSolutions().get(i),
                    nn.getResult().getAllPathsCosts().get(i));
        }
        System.out.println("\nAfter Local Search: ");
        System.out.println("Min: " + localSearch.getResult().getMinValue());
        System.out.println("Max: " + localSearch.getResult().getMaxValue());
        System.out.println("Avg: " + localSearch.getResult().getAvgValue());
        System.out.print("LocalSearchPath: ");
        for (Integer node : localSearch.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }
        System.out.println("\nCzas: " + localSearch.getResult().getTime());


        GraspNN graspNN = new GraspNN(graph);
        graspNN.execute();
        System.out.println("\n\nGraspNN: ");
        System.out.println("Min: " + graspNN.getResult().getMinValue());
        System.out.println("Max: " + graspNN.getResult().getMaxValue());
        System.out.println("Avg: " + graspNN.getResult().getAvgValue());

        System.out.print("Path: ");
        for (Integer node : graspNN.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }
        localSearch = new LocalSearchExecution(graph);
        for (int i = 0; i < graspNN.getResult().getAllSolutions().size(); i++) {
            localSearch.execute(graspNN.getResult().getAllSolutions().get(i),
                    graspNN.getResult().getAllPathsCosts().get(i));
        }
        System.out.println("\nAfter Local Search: ");
        System.out.println("Min: " + localSearch.getResult().getMinValue());
        System.out.println("Max: " + localSearch.getResult().getMaxValue());
        System.out.println("Avg: " + localSearch.getResult().getAvgValue());
        System.out.print("LocalSearchPath: ");
        for (Integer node : localSearch.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }


        GreedyCycle gc = new GreedyCycle(graph);
        gc.execute();
        System.out.println("\n\nGreedyCycle: ");
        System.out.println("Min: " + gc.getResult().getMinValue());
        System.out.println("Max: " + gc.getResult().getMaxValue());
        System.out.println("Avg: " + gc.getResult().getAvgValue());

        System.out.print("Path: ");
        for (Integer node : gc.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }
        localSearch = new LocalSearchExecution(graph);
        for (int i = 0; i < gc.getResult().getAllSolutions().size(); i++) {
            localSearch.execute(gc.getResult().getAllSolutions().get(i),
                    gc.getResult().getAllPathsCosts().get(i));
        }
        System.out.println("\nAfter Local Search: ");
        System.out.println("Min: " + localSearch.getResult().getMinValue());
        System.out.println("Max: " + localSearch.getResult().getMaxValue());
        System.out.println("Avg: " + localSearch.getResult().getAvgValue());
        System.out.print("LocalSearchPath: ");
        for (Integer node : localSearch.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }



        GraspGreedyCycle graspGreedyCycle = new GraspGreedyCycle(graph);
        graspGreedyCycle.execute();
        System.out.println("\n\nGraspGreedyCycle: ");
        System.out.println("Min: " + graspGreedyCycle.getResult().getMinValue());
        System.out.println("Max: " + graspGreedyCycle.getResult().getMaxValue());
        System.out.println("Avg: " + graspGreedyCycle.getResult().getAvgValue());

        System.out.print("Path: ");
        for (Integer node : graspGreedyCycle.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }
        localSearch = new LocalSearchExecution(graph);
        for (int i = 0; i < graspGreedyCycle.getResult().getAllSolutions().size(); i++) {
            localSearch.execute(graspGreedyCycle.getResult().getAllSolutions().get(i),
                    graspGreedyCycle.getResult().getAllPathsCosts().get(i));
        }
        System.out.println("\nAfter Local Search: ");
        System.out.println("Min: " + localSearch.getResult().getMinValue());
        System.out.println("Max: " + localSearch.getResult().getMaxValue());
        System.out.println("Avg: " + localSearch.getResult().getAvgValue());
        System.out.print("LocalSearchPath: ");
        for (Integer node : localSearch.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }



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
