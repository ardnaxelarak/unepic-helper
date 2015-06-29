package unepic;

import unepic.data.*;

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;
import java.util.Scanner;
import javax.json.*;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class DataServlet extends HttpServlet
{
    private class DatasetHandler extends DefaultHandler
    {
        private String name;
        private Dataset data = null;
        private Writer stream;
        private int catDepth;
        private SkillCategory curCategory = null;

        public DatasetHandler(String name, Writer stream)
        {
            this.name = name;
            this.stream = stream;
        }

        public void startDocument() throws SAXException
        {
            data = new Dataset(name);
            curCategory = null;
            catDepth = 0;
        }

        public void startElement(String uri, String localName,
                                 String qName, Attributes atts)
            throws SAXException
        {
            if (localName.equals("category"))
            {
                catDepth++;
                if (curCategory != null)
                {
                    log.warning("Nested category encountered: ignoring");
                    return;
                }

                String name = null;
                int attLen = atts.getLength();
                String curAtt;
                for (int i = 0; i < attLen; i++)
                {
                    curAtt = atts.getLocalName(i);
                    if (curAtt.equals("name"))
                    {
                        if (name != null)
                            log.warning("Duplicate category name attribute encountered: overwriting");
                        name = atts.getValue(i);
                    }
                    else
                    {
                        log.warning("Unrecognized category attribute '" + curAtt + "' encountered: ignoring");
                    }
                }

                curCategory = new SkillCategory(name);
            }
            else if (localName.equals("skill"))
            {
                if (curCategory == null)
                {
                    log.warning("Orphaned skill tag encountered: ignoring");
                    return;
                }

                String name = null,
                       essence = null,
                       display = null;
                int start = Integer.MIN_VALUE;
                int attLen = atts.getLength();
                String curAtt;

                // parse attributes
                for (int i = 0; i < attLen; i++)
                {
                    curAtt = atts.getLocalName(i);
                    if (curAtt.equals("name"))
                    {
                        if (name != null)
                            log.warning("Duplicate skill name attribute encountered: overwriting");
                        name = atts.getValue(i);
                    }
                    else if (curAtt.equals("start"))
                    {
                        if (start != Integer.MIN_VALUE)
                            log.warning("Duplicate skill start attribute encountered: overwriting");
                        start = Integer.parseInt(atts.getValue(i));
                    }
                    else if (curAtt.equals("display"))
                    {
                        if (display != null)
                            log.warning("Duplicate skill display attribute encountered: overwriting");
                        display = atts.getValue(i);
                    }
                    else if (curAtt.equals("essence"))
                    {
                        if (essence != null)
                            log.warning("Duplicate skill essence attribute encountered: overwriting");
                        essence = atts.getValue(i);
                    }
                    else
                    {
                        log.warning("Unrecognized skill attribute '" + curAtt + "' encountered: ignoring");
                    }
                }
                curCategory.addSkill(new Skill(name, start, display, essence));
            }
            else
            {
                log.warning("Unrecognized tag '" + localName + "' encountered: ignoring");
            }
        }

        public void endElement(String uri, String localName, String qName)
            throws SAXException
        {
            if (localName.equals("category"))
            {
                catDepth--;
                if (catDepth == 0)
                {
                    data.addCategory(curCategory);
                    curCategory = null;
                }
            }
        }

        public void endDocument() throws SAXException
        {
            JsonWriter writer = Json.createWriter(stream);
            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            writer.writeObject(data.buildJSON(factory).build());
        }
    }

    private static final Logger log = Logger.getLogger(DataServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        String profile = req.getParameter("profile");
        if (profile != null)
        {
            PrintWriter pw = resp.getWriter();
            resp.setContentType("text/json;charset=UTF-8");

            // profile = "multiplayer";
            String filename = "datafiles/" + profile + ".xml";

            InputStream in = null;

            try
            {
                in = new FileInputStream(new File(filename));
                SAXParserFactory spf = SAXParserFactory.newInstance();
                spf.setNamespaceAware(true);
                SAXParser saxParser = spf.newSAXParser();
                saxParser.parse(in, new DatasetHandler(profile, pw));
                in.close();
                pw.close();
            }
            catch (ParserConfigurationException e)
            {
                e.printStackTrace();
            }
            catch (SAXException e)
            {
                e.printStackTrace();
            }
            catch (FileNotFoundException e)
            {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            finally
            {
                if (in != null)
                    in.close();

                pw.close();
            }
        }
        else
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
