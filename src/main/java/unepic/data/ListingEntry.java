package unepic.data;

import java.util.*;
import javax.json.*;

public class ListingEntry implements JsonableObject
{
    private String category, name, display;
    private int level, cost;
    private String src;

    public ListingEntry(String category, String name, String display, int level, int cost, String src)
    {
        this.category = category;
        this.name = name;
        this.display = display;
        this.level = level;
        this.cost = cost;
        this.src = src;
    }

    @Override
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory)
    {
        JsonObjectBuilder job = factory.createObjectBuilder();

        if (category != null)
            job.add("type", category);
        else
            job.add("type", "unknown");
        
        if (name != null)
            job.add("name", name);
        else
            job.addNull("name");

        if (display != null)
            job.add("display", display);
        else
            job.addNull("display");

        if (level != Integer.MIN_VALUE)
            job.add("level", level);
        else
            job.addNull("level");

        if (cost != Integer.MIN_VALUE)
            job.add("cost", cost);
        else
            job.addNull("cost");

        if (src != null)
            job.add("src", src);
        else
            job.addNull("src");

        return job;
    }
}
