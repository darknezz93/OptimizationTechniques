package application;

import application.Edge;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 04.10.2016.
 */

public class Vertex {


    @XmlElements({
        @XmlElement(name="edge", type= Edge.class)
    })
    private List<Edge> edges = new ArrayList<>();

    public List<Edge> getEdges() {
        return edges;
    }
}
