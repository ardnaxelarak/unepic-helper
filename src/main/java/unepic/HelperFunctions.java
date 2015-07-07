package unepic;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.*;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.*;
import javax.servlet.http.*;

public class HelperFunctions
{
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static UserService userService = UserServiceFactory.getUserService();

    private HelperFunctions()
    {
    }

	public static String getRequestContent(HttpServletRequest req)
		throws IOException
	{
		StringBuilder sb = new StringBuilder();
		Reader r = req.getReader();
		char [] cbuf = new char[1024];
		int nread;

		while ( (nread = r.read(cbuf)) != -1 )
		{
			sb.append(cbuf, 0, nread);
		}

		return sb.toString();
	}

    public static String sha256(String data)
    {
        return DigestUtils.sha256Hex(data);
    }

    public static DatastoreService getDatastore()
    {
        return datastore;
    }

    public static User getCurrentUser()
    {
        return userService.getCurrentUser();
    }

    public static Key getUserKey()
    {
        User user = getCurrentUser();
        if (user == null)
            return null;

        Key userKey = KeyFactory.createKey("User", user.getUserId());
        Entity userEnt = null;
        try {
            userEnt = datastore.get(userKey);
        } catch (EntityNotFoundException e) {
            userEnt = new Entity(userKey);
            userEnt.setProperty("email", user.getEmail());
            userEnt.setProperty("nickname", user.getNickname());
            datastore.put(userEnt);
        }
        return userKey;
    }
}
