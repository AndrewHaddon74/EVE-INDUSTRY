

//"https://api.evemarketer.com/ec/marketstat?usesystem=30000142&typeid=34"


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class XML {

    public XML() {

    }

    public String getMinSell(int typeid) throws IOException, SAXException, ParserConfigurationException {

        String url = "https://api.evemarketer.com/ec/marketstat?usesystem=30000142&typeid="+typeid;

        //uses document reader to import the xml data
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());


        NodeList list = doc.getElementsByTagName("sell");
        Node node = list.item(0);
        Element eli = (Element) node;
        String minSell = eli.getElementsByTagName("min").item(0).getTextContent();


        return minSell;
    }

    public String getBasePrice(String name)throws IOException, SAXException, ParserConfigurationException{
        String url = "https://api.eve-industry.org/job-base-cost.xml?names="+name;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());

        NodeList list = doc.getElementsByTagName("");


        Node node = list.item(0);


        System.out.println();

        //String test = new URL(url).openStream();




//        String minSell = eli.getElementsByTagName("min").item(0).getTextContent();


        return null;
    }



}
