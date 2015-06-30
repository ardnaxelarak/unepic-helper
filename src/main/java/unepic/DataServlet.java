package unepic;

import unepic.data.*;
import unepic.parsing.*;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;
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
        private Writer stream;
        private RootParser root = null;
        private LinkedList<ElementParser> stack;

        public DatasetHandler(String name, Writer stream)
        {
            this.name = name;
            this.stream = stream;
        }

        public void startDocument() throws SAXException
        {
            root = new RootParser();
            stack = new LinkedList<ElementParser>();
            stack.add(root);
        }

        public void startElement(String uri, String localName,
                                 String qName, Attributes atts)
            throws SAXException
        {
            ElementParser cur = stack.getLast();
            ElementParser next = cur.startChild(uri, localName, qName, atts);
            next.startElement(uri, localName, qName, atts);
            stack.add(next);
        }

        public void endElement(String uri, String localName, String qName)
            throws SAXException
        {
            ElementParser cur = stack.removeLast();
            cur.endElement(uri, localName, qName);
            ElementParser prev = stack.getLast();
            prev.endChild(cur);
        }

        public void endDocument() throws SAXException
        {
            JsonWriter writer = Json.createWriter(stream);
            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            Dataset data = root.getDataset();
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
