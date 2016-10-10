package algorithms;

import application.Edge;
import application.Graph;
import application.Result;
import application.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 08.10.16.
 */
public class GreedyCycle {

    private List<Vertex> graphVertexes = new ArrayList<>();
    private Result result;
    private Integer pathCost = 0;


    public GreedyCycle(Graph graph) {
        graphVertexes = graph.getVertexes();
        result = new Result();
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

    public void execute() {

        for(Vertex vertex : graphVertexes) {
            pathCost = 0;
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
                List<Integer> bestSolution = new ArrayList<>(3); //vertex1, vertex2, newVertex;
                Integer bestValue = 0;

                //szukanie nowego wierzchoka
                for(int i = 1; i < visitedVertexesIds.size(); i++) {
                    for(Integer newVertex : notVisited) {

                        Integer cost = getTotalConnectionCost(visitedVertexesIds.get(i-1), visitedVertexesIds.get(i), newVertex);
                        if(bestValue == 0 || cost < bestValue) {
                            bestValue = cost;
                            if(bestSolution.size() > 0 ) {
                                bestSolution.set(0, visitedVertexesIds.get(i-1));
                                bestSolution.set(1, visitedVertexesIds.get(i));
                                bestSolution.set(2, newVertex);
                            } else {
                                bestSolution.add(0, visitedVertexesIds.get(i-1));
                                bestSolution.add(1, visitedVertexesIds.get(i));
                                bestSolution.add(2, newVertex);
                            }
                        }
                    }
                }
                pathCost += bestValue;
                visitedVertexesIds = updateVisitedVertexes(visitedVertexesIds, bestSolution);
            }

            fillResult(visitedVertexesIds);
        }
        result.setAvgValue(result.getAvgValue()/100);

    }
}
