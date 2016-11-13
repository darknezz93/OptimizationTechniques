package application;

import algorithms.EdgeSolution;
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
        printPopulation(population);
        for (int i = 0; i < 10000; i++) {
            Pair<Integer, Integer> randomIndexes = getRandomIndexes(20);
            Pair<List<Integer>, Integer> firstSolution = population.get(randomIndexes.getKey());
            Pair<List<Integer>, Integer> secondSolution = population.get(randomIndexes.getValue());

            List<Pair<Integer, Integer>> similarEdgesList = SimilarityExecution.getSimilarEdgesList(firstSolution.getKey(), secondSolution.getKey());
            List<Integer> similarVerticesList = SimilarityExecution.getSimilarVerticesList(firstSolution.getKey(), secondSolution.getKey());

            Pair<List<Integer>, Integer> combinedSolution = createCombinedSolution(similarEdgesList, similarVerticesList);
            LocalSearchExecution ls = new LocalSearchExecution(graph);
            ls.execute(combinedSolution.getKey(), combinedSolution.getValue());
            updatePopulationIfPossible(population, ls.getResult().getBestSolution(), ls.getResult().getMinValue());
        }
        printPopulation(population);
    }

    private void updatePopulationIfPossible(List<Pair<List<Integer>, Integer>> population, List<Integer> solution, int solutionPathCost) {
        int maxPopulation = -1;
        int maxPopulationIdx = -1;
        for (int i = 0; i < population.size(); i++) {
            Pair<List<Integer>, Integer> populationIth = population.get(i);
            if (populationIth.getValue().equals(solutionPathCost)) {
                return;
            }
            if (populationIth.getValue() > maxPopulation) {
                maxPopulation = populationIth.getValue();
                maxPopulationIdx = i;
            }
        }
        if (solutionPathCost < maxPopulation) {
            population.remove(maxPopulationIdx);
            population.add(new Pair<>(solution, solutionPathCost));
        }
    }

    private Pair<List<Integer>, Integer> createCombinedSolution(List<Pair<Integer, Integer>> similarEdgesList, List<Integer> similarVerticesList) {
        List<Integer> uniqueVertices = filterVerticesUsedInEdges(similarEdgesList, similarVerticesList);
        List<List<Pair<Integer, Integer>>> sequences = filterEdgeSequences(similarEdgesList);
        List<Integer> unusedVertices = createUnusedVerticesList(similarVerticesList);
        List<Integer> randVertices = new ArrayList<>();
        while (similarVerticesList.size() + randVertices.size() < 50) {
            int randIdx = (int) Math.floor(Math.random() * unusedVertices.size());
            Integer val = unusedVertices.get(randIdx);
            unusedVertices.remove(val);
            randVertices.add(val);
        }
        return createCombinedSolution(sequences, uniqueVertices, randVertices);
    }

    private Pair<List<Integer>, Integer> createCombinedSolution(List<List<Pair<Integer, Integer>>> sequences, List<Integer> uniqueVertices, List<Integer> randVertices) {
        List<Integer> combinedSolution = new ArrayList<>();
        List<Object> container = new ArrayList<>();
        container.addAll(sequences);
        container.addAll(uniqueVertices);
        container.addAll(randVertices);
        while (container.size() > 0) {
            int randIdx = (int) Math.floor(Math.random() * container.size());
            Object value = container.get(randIdx);
            container.remove(value);
            if (value instanceof Integer) {
                combinedSolution.add((Integer) value);
            } else {
                addSequenceToSolution(combinedSolution, (List<Pair<Integer, Integer>>) value);
            }
        }
        combinedSolution.add(combinedSolution.get(0));
        int pathCost = EdgeSolution.calculatePathLength(graph, combinedSolution);
        return new Pair<>(combinedSolution, pathCost);
    }

    private void addSequenceToSolution(List<Integer> combinedSolution, List<Pair<Integer, Integer>> sequence) {
        for (int i = 0; i < sequence.size(); i++) {
            if (i == 0) {
                combinedSolution.add(sequence.get(i).getKey());
            }
            combinedSolution.add(sequence.get(i).getValue());
        }
    }


    private List<Integer> createUnusedVerticesList(List<Integer> similarVerticesList) {
        List<Integer> unusedVertices = createFullVerticesList();
        for (int i = 0; i < similarVerticesList.size(); i++) {
            unusedVertices.remove(similarVerticesList.get(i));
        }
        return unusedVertices;
    }

    private static List<Integer> createFullVerticesList() {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            res.add(new Integer(i));
        }
        return res;
    }

    private List<Integer> filterVerticesUsedInEdges(List<Pair<Integer, Integer>> similarEdgesList, List<Integer> similarVerticesList) {
        List<Integer> res = new ArrayList<>(similarVerticesList);
        for (Pair<Integer, Integer> edge : similarEdgesList) {
            res.remove(edge.getKey());
            res.remove(edge.getValue());
        }
        return res;
    }

    private Pair<Integer, Integer> getRandomIndexes(int size) {
        int randIdx1 = (int) Math.floor(Math.random() * size);
        int randIdx2 = (int) Math.floor(Math.random() * size);
        while (randIdx1 == randIdx2) {
            randIdx2 = (int) Math.floor(Math.random() * size);
        }
        return new Pair<>(randIdx1, randIdx2);
    }

    private List<List<Pair<Integer, Integer>>> filterEdgeSequences(List<Pair<Integer, Integer>> edgesList) {
        List<List<Pair<Integer, Integer>>> sequences = new ArrayList<>();
        for (Pair<Integer, Integer> edge : edgesList) {
            boolean addedToSequence = false;
            for (List<Pair<Integer, Integer>> sequence : sequences) {
                addedToSequence = tryToAddToSequence(sequence, edge);
            }
            if (!addedToSequence) {
                List<Pair<Integer, Integer>> newSeq = new ArrayList<>();
                newSeq.add(edge);
                sequences.add(newSeq);
            }
        }
        validateSequencesConnected(sequences);
        return sequences;
    }

    private void validateSequencesConnected(List<List<Pair<Integer, Integer>>> sequences) {
        if (sequences.size() > 1) {
            List<Pair<Integer, Integer>> firstSequence = sequences.get(0);
            List<Pair<Integer, Integer>> lastSequence = sequences.get(sequences.size() - 1);
            if (firstSequence.get(0).getKey().equals(lastSequence.get(lastSequence.size() - 1).getValue())) {
                sequences.remove(firstSequence);
                lastSequence.addAll(firstSequence);
            }
        }
    }

    private boolean tryToAddToSequence(List<Pair<Integer, Integer>> sequence, Pair<Integer, Integer> edge) {
        Integer lastEdgeInSequence = sequence.get(sequence.size() - 1).getValue();
        if (lastEdgeInSequence == edge.getKey()) {
            sequence.add(edge);
            return true;
        }
        return false;
    }

    private void printPopulation(List<Pair<List<Integer>, Integer>> population) {
        System.out.println("Population values:\n");
        for (Pair<List<Integer>, Integer> pop : population) {
            System.out.print("" + pop.getValue() + ", ");
        }
        System.out.println();
    }
}
