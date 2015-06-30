package unepic.parsing;

import unepic.data.Dataset;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DatasetParser implements ElementParser
{
    private Dataset dataset = null;

    public void startElement(String uri, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
        int len = atts.getLength();
        String curatt;

        String name = null;
        for (int i = 0; i < len; i++)
        {
            curatt = atts.getLocalName(i);
            if (curatt.equals("name"))
            {
                if (name != null)
                    System.err.println("duplicate 'name' attribute found: overwriting");

                name = atts.getValue(i);
            }
            else
            {
                System.err.println("unexpected attribute '" + curatt + "' found in dataset element: ignoring");
            }
        }

        dataset = new Dataset(name);
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
    }

    public ElementParser startChild(String uri, String localName,
                                    String qName, Attributes atts)
        throws SAXException
    {
        if (localName.equals("category"))
        {
            return new CategoryParser();
        }
        else
        {
            System.err.println("unrecognized dataset child element '" + localName + "': ignoring");
            return new IgnoreParser();
        }
    }

    public void endChild(ElementParser child)
    {
        if (child instanceof CategoryParser)
        {
            dataset.addCategory(((CategoryParser)child).getCategory());
        }
    }

    public Dataset getDataset()
    {
        return dataset;
    }
}
