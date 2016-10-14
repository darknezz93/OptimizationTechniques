package application;

import algorithms.*;
import application.AlgorithmService;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by student on 04.10.2016.
 */
public class Main {

    public static void main(String [] args) throws JAXBException {
        AlgorithmService as = new AlgorithmService();
        Graph graph = as.getGraphFromFile("data" + File.separator + "kroA100.xml");

        NearestNeighbour nn = new NearestNeighbour(graph);
        nn.execute();
        System.out.println("NN: ");
        System.out.println("Min: " + nn.getResult().getMinValue());
        System.out.println("Max: " + nn.getResult().getMaxValue());
        System.out.println("Avg: " + nn.getResult().getAvgValue());

        System.out.print("Path: ");
        for(Integer node : nn.getResult().getBestSolution()) {
            System.out.print(node + ", ");
        }

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
        }

    }
}
