package application;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * Created by student on 04.10.2016.
 */
public class AlgorithmService {

    public Graph getGraphFromFile(String filePath) throws JAXBException {

        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(Graph.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Graph graph = (Graph) jaxbUnmarshaller.unmarshal(file);

        return graph;
    }

}
