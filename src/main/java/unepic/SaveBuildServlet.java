package unepic;

import java.io.*;
import java.util.logging.Logger;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.*;
import javax.json.*;
import javax.servlet.http.*;

import static unepic.HelperFunctions.*;

public class SaveBuildServlet extends HttpServlet
{
    private static final Logger log = Logger.getLogger(SaveBuildServlet.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        String charname = req.getParameter("character");
        String levelStr = req.getParameter("level");
        if (charname == null || levelStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        int level = 0;
        try {
            level = Integer.parseInt(levelStr);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        doPostSave(charname, level, req, resp);
    }

    public void doPostSave(String charname, int level,
        HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        log.info("Saving character " + charname + ", level " + level);

        String content = getRequestContent(req);

        log.info(content);

        DatastoreService datastore = getDatastore();
        Transaction txn = datastore.beginTransaction();

        try {
            Key userKey = getUserKey();
            String keyHash = sha256(charname + level);
            Entity ent = new Entity("Build", keyHash, userKey);
            ent.setProperty("character", charname);
            ent.setProperty("level", level);
            ent.setProperty("content", new Text(content));
            datastore.put(ent);
            txn.commit();
            log.info("saved build");

            resp.setContentType("text/json;charset=UTF-8");
            JsonWriter writer = Json.createWriter(resp.getWriter());
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("status", "ok");
            writer.writeObject(job.build());
            writer.close();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
