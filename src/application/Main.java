package application;

import algorithms.GreedyCycle;
import algorithms.NearestNeighbour;
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

    }
}
