import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by student on 04.10.2016.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Edge {

    @XmlAttribute
    private double cost;

    @XmlValue
    private int nodeId;
}
