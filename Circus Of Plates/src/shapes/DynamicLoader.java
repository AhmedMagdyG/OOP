package shapes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Stack;

import org.apache.log4j.Logger;

public class DynamicLoader {
	private static final Logger LOGGER = Logger.getLogger(DynamicLoader.class);

	private ClassLoader classLoader;

	public DynamicLoader() {
	}

	public Constructor<?>[] loadClass(String path) {
		File file = new File(path);
		String newClass = getNewShapeName(path);
		if (path == null)
			return null;
		try {
			URL url = file.toURI().toURL();
			URL[] urls = new URL[] { url };
			classLoader = new URLClassLoader(urls);
			Class<?> cls = classLoader.loadClass("shapes." + newClass);
			LOGGER.info("Successful class loading");
			return cls.getConstructors();
		} catch (MalformedURLException | ClassNotFoundException e) {
			LOGGER.error("Unsuccessful class loading");
		}
		return null;
	}

	private String getNewShapeName(String path) {
		Stack<Character> reversedPath = new Stack<Character>();
		StringBuilder retNewShapeName = new StringBuilder("");
		int len = path.length();
		if (len <= 4 || path.charAt(len - 4) != '.') {
			return null;
		}
		int nameIndex = len - 5;
		while (nameIndex >= 0 && path.charAt(nameIndex) != File.separatorChar) {
			reversedPath.push(path.charAt(nameIndex));
			nameIndex--;
		}
		while (!reversedPath.isEmpty()) {
			retNewShapeName.append(reversedPath.pop());
		}
		LOGGER.info("Shape name obtained");
		return retNewShapeName.toString();
	}

}
