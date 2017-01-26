package shapes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;

import org.apache.log4j.Logger;


public class DynamicLoader {
	private static final Logger LOGGER = Logger.getLogger(DynamicLoader.class);

	private static DynamicLoader dlInstance;

	private ClassLoader classLoader;
	
	private DynamicLoader() {
	}
	
	public static DynamicLoader getInstance() {
		if(dlInstance == null) {
			dlInstance = new DynamicLoader();
		}
		return dlInstance;
	}
	
	public ArrayList<Constructor<?>[]> initialize() {
		Properties prop = new Properties();
		InputStream input = null;
		ArrayList<Constructor<?>[]> ret = new ArrayList<Constructor<?>[]>();
		try {

			input = new FileInputStream("config.properties");

			prop.load(input);

			Constructor<?>[] cons1 = loadClass(prop.getProperty("firstClass"));
			Constructor<?>[] cons2 = loadClass(prop.getProperty("secondClass"));
			ret.add(cons1);
			ret.add(cons2);
			return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("HERe");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private Constructor<?>[] loadClass(String path) {
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
			System.out.println("HERE");
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
