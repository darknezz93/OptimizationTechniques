package application;

import algorithms.NearestNeighbour;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inf109683 on 08.11.2016.
 */
public class Evolution {

    private Graph graph;

    public Evolution(Graph graph) {
        this.graph = graph;
    }

    public void execute() {
        NearestNeighbour nn = new NearestNeighbour(graph);
        nn.execute();

        List<Pair<List<Integer>, Integer>> population = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            LocalSearchExecution ls = new LocalSearchExecution(graph);
            ls.execute(nn.getResult().getAllSolutions().get(i), nn.getResult().getAllPathsCosts().get(i));
            population.add(new Pair<>(ls.getResult().getBestSolution(), ls.getResult().getMinValue()));
        }

        for (int i = 0; i < 100; i++) {
            Pair<Integer, Integer> randomIndexes = getRandomIndexes(20);
            Pair<List<Integer>, Integer> firstSolution = population.get(randomIndexes.getKey());
            Pair<List<Integer>, Integer> secondSolution = population.get(randomIndexes.getValue());

            List<Pair<Integer, Integer>> similarEdgesList = SimilarityExecution.getSimilarEdgesList(firstSolution.getKey(), secondSolution.getKey());
            List<Integer> similarVerticesList = SimilarityExecution.getSimilarVerticesList(firstSolution.getKey(), secondSolution.getKey());

            createCombinedSolution(similarEdgesList, similarVerticesList);

        }
    }

    private Pair<List<Integer>, Integer> createCombinedSolution(List<Pair<Integer, Integer>> similarEdgesList, List<Integer> similarVerticesList) {
        filterVerticesUsedInEdges(similarEdgesList, similarVerticesList);
        List<List<Pair<Integer, Integer>>> sequences = filterEdgeSequences(similarEdgesList);
        //TODO implement this
        return null;
    }

    private void filterVerticesUsedInEdges(List<Pair<Integer, Integer>> similarEdgesList, List<Integer> similarVerticesList) {
        for(Pair<Integer, Integer> edge : similarEdgesList){
            similarVerticesList.remove(edge.getKey());
            similarVerticesList.remove(edge.getValue());
        }
    }

    private Pair<Integer, Integer> getRandomIndexes(int size) {
        int randIdx1 = (int) Math.floor(Math.random() * size);
        int randIdx2 = (int) Math.floor(Math.random() * size);
        while (randIdx1 == randIdx2) {
            randIdx2 = (int) Math.floor(Math.random() * size);
        }
        return new Pair<>(randIdx1, randIdx2);
    }

    private List<List<Pair<Integer, Integer>>> filterEdgeSequences(List<Pair<Integer, Integer>> edgesList){
        List<List<Pair<Integer, Integer>>> sequences = new ArrayList<>();
        for(Pair<Integer, Integer> edge : edgesList){
            boolean addedToSequence = false;
            for(List<Pair<Integer, Integer>> sequence : sequences){
                addedToSequence = tryToAddToSequence(sequence, edge);
            }
            if(!addedToSequence){
                List<Pair<Integer, Integer>> newSeq = new ArrayList<>();
                newSeq.add(edge);
                sequences.add(newSeq);
            }
        }
        return sequences;
    }

    private boolean tryToAddToSequence(List<Pair<Integer, Integer>> sequence, Pair<Integer, Integer> edge) {
        //TODO implement this
        return false;
    }
}
