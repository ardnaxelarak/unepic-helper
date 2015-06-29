package unepic.data;

import java.util.*;
import javax.json.*;

public class SkillCategory implements Jsonable
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
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory)
    {
        JsonObjectBuilder job = factory.createObjectBuilder();
        if (name != null)
            job.add("name", name);
        else
            job.addNull("name");

        JsonArrayBuilder jab = factory.createArrayBuilder();
        for (Skill skill : skills)
            jab.add(skill.buildJSON(factory));
        job.add("skills", jab);
        return job;
    }
}
