package algorithms;

import application.*;

import java.util.*;

/**
 * Created by adam on 08.10.16.
 */
public class GraspNN {

    private Result result;
    private List<Vertex> graphVertexes = new ArrayList<>();
    private Integer pathCost = 0;


    public GraspNN(Graph graph) {
        result = new Result();
        graphVertexes = graph.getVertexes();
    }

    public Edge findNewEdge(Vertex vertex, List<Integer> visitedVertexesIds) {
        List<Edge> notVisited = retrievePossibleEdges(vertex, visitedVertexesIds);
        List<Edge> top3Edges = find3TopEdges(notVisited);
        Random random = new Random();
        Edge edge = top3Edges.get(random.nextInt(top3Edges.size()));
        return edge;
    }

    public List<Edge> find3TopEdges(List<Edge> edges) {
        List<Edge> top3Edges = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            Edge edge = findMinCostEdge(edges);
            top3Edges.add(edge);
            edges.remove(edge);
        }
        return top3Edges;
    }

    public Edge findMinCostEdge(List<Edge> edges) {
        Edge minEdge = edges.get(0);
        for(Edge edge : edges) {
            if(edge.getCost() < minEdge.getCost()) {
                minEdge = edge;
            }
        }
        return minEdge;
    }

    public List<Edge> retrievePossibleEdges(Vertex vertex, List<Integer> visitedVertexesIds) {
        List<Edge> notVisited = new ArrayList<>();
        for(Edge edge : vertex.getEdges()) {
            if(!visitedVertexesIds.contains(edge.getVertexId())) {
                notVisited.add(edge);
            }
        }
        return notVisited;
    }

    private void fillResult(List<Integer> visitedVertexesIds) {
        if(result.getMinValue() == 0 || result.getMinValue() > pathCost) {
            result.setMinValue(pathCost);
            result.setBestSolution(visitedVertexesIds);
        } else if(result.getMaxValue() < pathCost) {
            result.setMaxValue(pathCost);
        }
        result.addToAvgValue(pathCost);
        result.addToAllSolutions(visitedVertexesIds);
        result.addToAllPathsCosts(pathCost);
    }

    public List<Integer> addLastEdgeCost(Vertex vertex, List<Integer> visitedVertexesIds) {
        List<Edge> edges = vertex.getEdges();
        for(Edge edge : edges) {
            if(edge.getVertexId() == visitedVertexesIds.get(0)) {
                pathCost += edge.getCost();
                visitedVertexesIds.add(visitedVertexesIds.get(0));
            }
        }
        return visitedVertexesIds;
    }

    public void execute() {

        for(Vertex vertex: graphVertexes) {

            Edge newEdge;
            Vertex tempVertex = vertex;
            pathCost = 0;

            List<Integer> visitedVertexesIds = new ArrayList<>();
            visitedVertexesIds.add(graphVertexes.indexOf(vertex));

            while(visitedVertexesIds.size() < 50) {
                newEdge = findNewEdge(tempVertex, visitedVertexesIds);
                tempVertex = graphVertexes.get(newEdge.getVertexId());
                visitedVertexesIds.add(newEdge.getVertexId());
                pathCost += newEdge.getCost();
            }

            visitedVertexesIds = addLastEdgeCost(tempVertex, visitedVertexesIds);

            fillResult(visitedVertexesIds);
        }
        result.setAvgValue(result.getAvgValue()/100);

    }

    public Result getResult() {
        return result;
    }
}
