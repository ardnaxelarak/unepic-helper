package unepic.data;

import java.util.*;
import javax.json.*;

public interface ListingEntry extends JsonableObject
{
    @Override
    public JsonObjectBuilder buildJSON(JsonBuilderFactory factory);
}
