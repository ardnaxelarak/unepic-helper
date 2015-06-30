package unepic.parsing;

import unepic.data.Dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RootParser implements ElementParser
{
    private Dataset dataset = null;

    public void startElement(String uri, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
    }

    public ElementParser startChild(String uri, String localName,
                                    String qName, Attributes atts)
        throws SAXException
    {
        if (localName.equals("dataset"))
        {
            if (dataset != null)
            {
                System.err.println("additional dataset found: ignoring");
                return new IgnoreParser();
            }
            else
            {
                return new DatasetParser();
            }
        }
        else
        {
            System.err.println("unrecognized root element '" + localName + "': ignoring");
            return new IgnoreParser();
        }
    }

    public void endChild(ElementParser child)
    {
        if (child instanceof DatasetParser)
        {
            dataset = ((DatasetParser)child).getDataset();
        }
    }

    public Dataset getDataset()
    {
        return dataset;
    }
}
