package unepic.data;

import java.util.*;
import javax.json.*;

public class Listing implements JsonableArray
{
    private String skill;
    private List<ListingEntry> entries;

    public Listing(String skill)
    {
        this.skill = skill;
        entries = new LinkedList<ListingEntry>();
    }

    public void addEntry(ListingEntry entry)
    {
        entries.add(entry);
    }

    public void mergeWith(Listing l)
    {
        if (!skill.equals(l.skill))
            throw new IllegalArgumentException();

        entries.addAll(l.entries);
    }

    public String getSkill()
    {
        return skill;
    }

    @Override
    public JsonArrayBuilder buildJSON(JsonBuilderFactory factory)
    {
        JsonArrayBuilder jab = factory.createArrayBuilder();
        for (ListingEntry entry : entries)
            jab.add(entry.buildJSON(factory));

        return jab;
    }
}
