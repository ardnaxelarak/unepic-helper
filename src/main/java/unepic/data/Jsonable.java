package unepic.data;

import javax.json.*;

public interface Jsonable
{
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory);
}
