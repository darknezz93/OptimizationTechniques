import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by student on 04.10.2016.
 */
public class Marshaller extends XmlAdapter<Double, Integer> {

    public Integer unmarshal(Double value)  {
        return ((int)Math.round(value));
    }

    @Override
    public Double marshal(Integer v) throws Exception {
        return null;
    }


}
