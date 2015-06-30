package unepic.parsing;

import unepic.data.Skill;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SkillParser implements ElementParser
{
    private Skill skill = null;

    public void startElement(String uri, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
        int len = atts.getLength();
        String curatt;

        String name = null;
        String essence = null;
        String display = null;
        int start = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++)
        {
            curatt = atts.getLocalName(i);
            if (curatt.equals("name"))
            {
                if (name != null)
                    System.err.println("duplicate 'name' attribute found: overwriting");

                name = atts.getValue(i);
            }
            else if (curatt.equals("start"))
            {
                if (start != Integer.MIN_VALUE)
                    System.err.println("duplicate 'start' attribute found: overwriting");

                start = Integer.parseInt(atts.getValue(i));
            }
            else if (curatt.equals("display"))
            {
                if (display != null)
                    System.err.println("duplicate 'display' attribute found: overwriting");

                display = atts.getValue(i);
            }
            else if (curatt.equals("essence"))
            {
                if (essence != null)
                    System.err.println("duplicate 'essence' attribute found: overwriting");

                essence = atts.getValue(i);
            }
            else
            {
                System.err.println("unexpected attribute '" + curatt + "' found in skill element: ignoring");
            }
        }

        skill = new Skill(name, start, display, essence);
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
    }

    public ElementParser startChild(String uri, String localName,
                                    String qName, Attributes atts)
        throws SAXException
    {
        {
            System.err.println("unrecognized skill child element '" + localName + "': ignoring");
            return new IgnoreParser();
        }
    }

    public void endChild(ElementParser child)
    {
    }

    public Skill getSkill()
    {
        return skill;
    }
}
