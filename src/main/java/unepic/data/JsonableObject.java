package unepic.data;

import javax.json.*;

public interface JsonableObject
{
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory);
}
