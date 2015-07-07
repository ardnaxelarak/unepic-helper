package unepic;

import java.io.*;
import java.util.logging.Logger;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.*;
import javax.json.*;
import javax.servlet.http.*;

import static unepic.HelperFunctions.*;

public class LoadBuildServlet extends HttpServlet
{
    private static final Logger log = Logger.getLogger(SaveBuildServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        String userId = req.getParameter("user");
        Key userKey = null;
        if (userId == null)
            userKey = getUserKey();
        else
            userKey = KeyFactory.createKey("User", userId);

        if (userKey == null)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        doGetBuilds(userKey, req, resp);
    }

    public void doGetBuilds(Key userKey, HttpServletRequest req,
            HttpServletResponse resp) throws IOException
    {
        DatastoreService datastore = getDatastore();

        JsonBuilderFactory factory = Json.createBuilderFactory(null);

        resp.setContentType("text/json;charset=UTF-8");
        JsonWriter writer = Json.createWriter(resp.getWriter());
        JsonObjectBuilder job = factory.createObjectBuilder();

        Query buildQuery = new Query("Build")
                                .setAncestor(userKey)
                                .addSort("character", Query.SortDirection.ASCENDING)
                                .addSort("level", Query.SortDirection.ASCENDING);

        String curChar = null;
        JsonObjectBuilder curObj = null;
        Text content = null;

        for (Entity build : datastore.prepare(buildQuery).asIterable())
        {
            if (!build.getProperty("character").toString().equals(curChar))
            {
                if (curObj != null)
                    job.add(curChar, curObj);

                curObj = factory.createObjectBuilder();
                curChar = build.getProperty("character").toString();
            }

            content = (Text)build.getProperty("content");

            curObj.add(build.getProperty("level").toString(),
                    getJson(content.getValue()));
        }

        if (curObj != null)
            job.add(curChar, curObj);

        writer.writeObject(job.build());
        writer.close();
    }
}
