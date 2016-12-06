package cc.holstr.imLoad.json;

import javax.json.Json;
import javax.json.JsonObject;

public class JsonGenerator {
	
	public static JsonObject getUploadImageJson(String image) {
		//create jsonObject to send to sheets api
		JsonObject newSheet = Json.createObjectBuilder()
				.add("image", image)
				.add("name", "base64")
				.build();
		return newSheet;
	}
	
}