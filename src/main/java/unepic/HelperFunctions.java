package unepic;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class HelperFunctions
{
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    private HelperFunctions()
    {
    }

    public static DatastoreService getDatastore()
    {
        return datastore;
    }

    public static Key getUserKey(String username)
    {
        Key userKey = KeyFactory.createKey("User", username);
        Entity userEnt = null;
        try {
            userEnt = datastore.get(userKey);
        } catch (EntityNotFoundException e) {
            userEnt = new Entity(userKey);
            datastore.put(userEnt);
        }
        return userKey;
    }
}
