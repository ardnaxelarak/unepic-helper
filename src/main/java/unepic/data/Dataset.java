package unepic.data;

import java.util.*;
import javax.json.*;

public class Dataset implements JsonableObject
{
    private String name;
    private List<SkillCategory> categories;
    private Hashtable<String, Listing> listings;

    public Dataset(String name)
    {
        this.name = name;
        categories = new LinkedList<SkillCategory>();
        listings = new Hashtable<String, Listing>();
    }

    public void addCategory(SkillCategory category)
    {
        categories.add(category);
    }

    public void addListing(Listing listing)
    {
        String skill = listing.getSkill();
        if (listings.containsKey(skill))
        {
            listings.get(skill).mergeWith(listing);
        }
        else
        {
            listings.put(skill, listing);
        }
    }

    @Override
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory)
    {
        JsonObjectBuilder job = factory.createObjectBuilder();
        if (name != null)
            job.add("name", name);
        else
            job.addNull("name");

        JsonArrayBuilder jab = factory.createArrayBuilder();
        for (SkillCategory category : categories)
            jab.add(category.buildJSON(factory));
        job.add("categories", jab);

        JsonObjectBuilder info = factory.createObjectBuilder();
        for (SkillCategory sc : categories)
        {
            for (Skill s : sc)
            {
                JsonObjectBuilder sk = s.buildJSON(factory);
                if (listings.containsKey(s.getName()))
                    sk.add("entries", listings.get(s.getName()).buildJSON(factory));
                else
                    sk.addNull("entries");
                info.add(s.getName(), sk);
            }
        }
        job.add("info", info);

        return job;
    }
}
