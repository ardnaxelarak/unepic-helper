package unepic.data;

import java.util.*;
import javax.json.*;

public class SkillCategory implements JsonableObject, Iterable<Skill>
{
    private String name;
    private List<Skill> skills;

    public SkillCategory(String name)
    {
        this.name = name;
        skills = new LinkedList<Skill>();
    }

    public void addSkill(Skill skill)
    {
        skills.add(skill);
    }

    @Override
    public Iterator<Skill> iterator()
    {
        return skills.iterator();
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
        for (Skill skill : skills)
            jab.add(skill.getName());
        job.add("skills", jab);
        return job;
    }
}
