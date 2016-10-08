package application;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by student on 04.10.2016.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Edge {

    @XmlAttribute
    @XmlJavaTypeAdapter(Marshaller.class)
    private Integer cost;

    @XmlValue
    private int vertexId;


    public Integer getCost() {
        return cost;
    }

    public int getVertexId() {
        return vertexId;
    }
}
