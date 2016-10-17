package algorithms;

import application.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by adam on 08.10.16.
 */
public class GraspGreedyCycle {

    private List<Vertex> graphVertexes = new ArrayList<>();
    private Result result;
    private ResultLocalSearch resultLocalSearch;
    private Integer pathCost = 0;
    private Integer localSearchPatchCost = 0;
    List<List<Integer>> top3Solutions;



    public GraspGreedyCycle(Graph graph) {
        graphVertexes = graph.getVertexes();
        result = new Result();
        resultLocalSearch = new ResultLocalSearch();
        top3Solutions = new ArrayList<>();
    }

    private Edge findMinCostEdge(List<Edge> edges) {
        Edge minEdge = edges.get(0);
        for(Edge edge : edges) {
            if(edge.getCost() < minEdge.getCost()) {
                minEdge = edge;
            }
        }
        return minEdge;
    }

    private List<Integer> retrieveNotVisited(Vertex vertex, List<Integer> visitedVertexesIds) {
        List<Integer> notVisited = new ArrayList<>();
        for(Edge edge : vertex.getEdges()) {
            if(!visitedVertexesIds.contains(edge.getVertexId())) {
                notVisited.add(edge.getVertexId());
            }
        }
        return notVisited;
    }

    private int getSingleConnectionCost(Integer vertex1, Integer vertex2) {
        Vertex vertex = graphVertexes.get(vertex1);
        Integer cost = 0;
        for(Edge edge : vertex.getEdges()) {
            if(edge.getVertexId() == vertex2) {
                cost = edge.getCost();
                break;
            }
        }
        return cost;
    }

    private int getTotalConnectionCost(Integer vertex1, Integer vertex2, Integer newVertex) {
        int singleConnectionCost = getSingleConnectionCost(vertex1, vertex2);
        int connection1Cost = getSingleConnectionCost(vertex1, newVertex);
        int connection2Cost = getSingleConnectionCost(vertex2, newVertex);
        int totalConnectionCost = connection1Cost + connection2Cost - singleConnectionCost;
        return totalConnectionCost;
    }

    private List<Integer> updateVisitedVertexes(List<Integer> visitedVertexesIds,
                                                List<Integer> bestSolution) {
        for(int i = 0; i < visitedVertexesIds.size(); i++) {
            if(visitedVertexesIds.get(i) == bestSolution.get(0)) {
                visitedVertexesIds.add(i+1, bestSolution.get(2));
                break;
            }
        }
        return visitedVertexesIds;
    }

    private void fillResultLocalSearch(List<Integer> visitedVertexesIds) {
        if(resultLocalSearch.getMinValue() == 0 || resultLocalSearch.getMinValue() > localSearchPatchCost) {
            resultLocalSearch.setMinValue(localSearchPatchCost);
            resultLocalSearch.setBestSolution(visitedVertexesIds);
        } else if(resultLocalSearch.getMaxValue() < localSearchPatchCost) {
            resultLocalSearch.setMaxValue(localSearchPatchCost);
        }
        resultLocalSearch.addToAvgValue(localSearchPatchCost);
    }

    private void fillResult(List<Integer> visitedVertexesIds) {
        if(result.getMinValue() == 0 || result.getMinValue() > pathCost) {
            result.setMinValue(pathCost);
            result.setBestSolution(visitedVertexesIds);
        } else if(result.getMaxValue() < pathCost) {
            result.setMaxValue(pathCost);
        }
        result.addToAvgValue(pathCost);
    }



    public Result getResult() {
        return result;
    }

    public void addToBestSolutions(List<Integer> bestSolution) {
        if(top3Solutions.size() < 3) {
            if(!top3Solutions.containsAll(bestSolution)) {
                top3Solutions.add(bestSolution);
            }
        } else {
            List<Integer> worst = getWorstPosition();
            if(!top3Solutions.containsAll(bestSolution) && bestSolution.get(3) < worst.get(3)) {
                int index = top3Solutions.indexOf(worst);
                top3Solutions.set(index, bestSolution);
            }
        }

    }


    public List<Integer> getWorstPosition() {
        List<Integer> worst = top3Solutions.get(0);
        for(List<Integer> li : top3Solutions) {
            if(li.get(3) > worst.get(3)) {
                worst = li;
            }
        }
        return worst;
    }

    public List<Integer> selectSolution() {
        Random random = new Random();
        List<Integer> solution = top3Solutions.get(random.nextInt(top3Solutions.size()));
        return solution;
    }

    public void execute() {

        for(Vertex vertex : graphVertexes) {
            pathCost = 0;
            //top3Solutions.clear();
            top3Solutions = new ArrayList<>();
            List<Integer> visitedVertexesIds = new ArrayList<>();
            visitedVertexesIds.add(graphVertexes.indexOf(vertex));
            Edge firstEdge = findMinCostEdge(vertex.getEdges());
            //w jedna i druga strone
            pathCost += firstEdge.getCost();
            pathCost += firstEdge.getCost();
            visitedVertexesIds.add(firstEdge.getVertexId());
            visitedVertexesIds.add(graphVertexes.indexOf(vertex));


            while(visitedVertexesIds.size() <= 50) {

                List<Integer> notVisited = retrieveNotVisited(vertex, visitedVertexesIds);
                List<Integer> bestSolution = new ArrayList<>(); //vertex1, vertex2, newVertex;
                top3Solutions = new ArrayList<>();

                //szukanie nowego wierzchoka
                for(int i = 1; i < visitedVertexesIds.size(); i++) {
                    for(Integer newVertex : notVisited) {
                        bestSolution = new ArrayList<>();
                        Integer cost = getTotalConnectionCost(visitedVertexesIds.get(i-1), visitedVertexesIds.get(i), newVertex);
                            if(bestSolution.size() > 0 ) {
                                bestSolution.set(0, visitedVertexesIds.get(i-1));
                                bestSolution.set(1, visitedVertexesIds.get(i));
                                bestSolution.set(2, newVertex);
                                bestSolution.set(3, cost);
                            } else {
                                bestSolution.add(0, visitedVertexesIds.get(i-1));
                                bestSolution.add(1, visitedVertexesIds.get(i));
                                bestSolution.add(2, newVertex);
                                bestSolution.add(3, cost);
                            }
                        addToBestSolutions(bestSolution);
                    }
                }
                List<Integer> selectedFromTop3 = selectSolution();
                pathCost += selectedFromTop3.get(3);
                visitedVertexesIds = updateVisitedVertexes(visitedVertexesIds, selectedFromTop3);
            }

            fillResult(visitedVertexesIds);
        }
        result.setAvgValue(result.getAvgValue()/100);

    }
}
