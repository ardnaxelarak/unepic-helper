package unepic.data;

import javax.json.*;

public interface JsonableArray
{
    public JsonArrayBuilder buildJSON(JsonBuilderFactory factory);
}
