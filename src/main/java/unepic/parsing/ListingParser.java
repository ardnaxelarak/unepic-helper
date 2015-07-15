package unepic.parsing;

import unepic.data.Listing;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ListingParser implements ElementParser
{
    private Listing listing = null;

    public void startElement(String uri, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
        int len = atts.getLength();
        String curatt;

        String skill = null;
        for (int i = 0; i < len; i++)
        {
            curatt = atts.getLocalName(i);
            if (curatt.equals("skill"))
            {
                if (skill != null)
                    System.err.println("duplicate 'skill' attribute found: overwriting");

                skill = atts.getValue(i);
            }
            else
            {
                System.err.println("unexpected attribute '" + curatt + "' found in listing element: ignoring");
            }
        }

        listing = new Listing(skill);
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
    }

    public ElementParser startChild(String uri, String localName,
                                    String qName, Attributes atts)
        throws SAXException
    {
        if (localName.equals("entry"))
        {
            return new EntryParser();
        }
        else
        {
            System.err.println("unrecognized listing child element '" + localName + "': ignoring");
            return new IgnoreParser();
        }
    }

    public void endChild(ElementParser child)
    {
        if (child instanceof EntryParser)
        {
            listing.addEntry(((EntryParser)child).getEntry());
        }
    }

    public Listing getListing()
    {
        return listing;
    }
}
