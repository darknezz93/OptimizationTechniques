package application;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 04.10.2016.
 */


@XmlRootElement
public class Graph {


    @XmlElements({
        @XmlElement(name="vertex", type= Vertex.class)
    })
    private List<Vertex> vertexes = new ArrayList<>();

    public List<Vertex> getVertexes() {
        return vertexes;
    }
}
