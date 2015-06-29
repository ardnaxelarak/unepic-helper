package unepic.data;

import java.util.*;
import javax.json.*;

public class Dataset implements Jsonable
{
    private String name;
    private List<SkillCategory> categories;

    public Dataset(String name)
    {
        this.name = name;
        categories = new LinkedList<SkillCategory>();
    }

    public void addCategory(SkillCategory category)
    {
        categories.add(category);
    }

    @Override
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory)
    {
        JsonObjectBuilder job = factory.createObjectBuilder();
        job.add("name", name);

        JsonArrayBuilder jab = factory.createArrayBuilder();
        for (SkillCategory category : categories)
            jab.add(category.buildJSON(factory));
        job.add("categories", jab);
        return job;
    }
}
