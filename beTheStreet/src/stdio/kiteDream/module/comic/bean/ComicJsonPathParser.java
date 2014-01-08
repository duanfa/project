package stdio.kiteDream.module.comic.bean;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class ComicJsonPathParser extends JsonSerializer<String> {
	public static String basePath;
	@Override
	public void serialize(String value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		String absolutePath = basePath+value;
		jgen.writeString(absolutePath);
	}

}
