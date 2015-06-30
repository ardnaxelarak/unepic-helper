package unepic.parsing;

import unepic.data.SkillCategory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CategoryParser implements ElementParser
{
    private SkillCategory category = null;

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
                System.err.println("unexpected attribute '" + curatt + "' found in category element: ignoring");
            }
        }

        category = new SkillCategory(name);
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
    }

    public ElementParser startChild(String uri, String localName,
                                    String qName, Attributes atts)
        throws SAXException
    {
        if (localName.equals("skill"))
        {
            return new SkillParser();
        }
        else
        {
            System.err.println("unrecognized category child element '" + localName + "': ignoring");
            return new IgnoreParser();
        }
    }

    public void endChild(ElementParser child)
    {
        if (child instanceof SkillParser)
        {
            category.addSkill(((SkillParser)child).getSkill());
        }
    }

    public SkillCategory getCategory()
    {
        return category;
    }
}
