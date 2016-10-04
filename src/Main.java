import javax.xml.bind.JAXBException;

/**
 * Created by student on 04.10.2016.
 */
public class Main {

    public static void main(String [] args) throws JAXBException {
        AlgorithmService as = new AlgorithmService();
        Graph graph = as.getGraphFromFile("C:\\Users\\student\\IdeaProjects\\TechnikiOptymalizacji\\data\\kroA100.xml");
        System.out.println(graph);
    }
}
