package web.util;

import java.io.File;

public class ResourceResolver {
	private static final String BASE_DIR = System.getProperty("user.home");
	
	public static File getUserFile() {
		File file = new File(BASE_DIR + "/" + "user-map.json");
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		return file;
	}
}