package codes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.thoughtworks.xstream.converters.Converter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExtensionLoader {
	private FileChooser fileChooser;
	private Stage stage;

	public ExtensionLoader(Stage stage) {
		fileChooser = new FileChooser();
		FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("*.jar", "*.jar");
		fileChooser.getExtensionFilters().add(exFilter);
		this.stage = stage;
	}

	@SuppressWarnings("null")
	public Class<?>[] load(File extensionFile) throws ClassNotFoundException, MalformedURLException {
		Class<?>[] loadedClasses = new Class<?>[2];
		System.out.println(loadedClasses);
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(extensionFile.getPath());
		} catch (IOException e1) {
			// Alert alert = new Alert(AlertType.ERROR);
			// alert.setTitle("LOAD FAILED!");
			// alert.setHeaderText("Failed to load data!");
			// alert.setContentText(
			// "A problem occured during loading this file.\n"
			// + "File may be corrupted or not (.jar) file");
			// alert.showAndWait();
			try {
				jarFile.close();
			} catch (IOException e2) {
			}
			return null;
		}
		Enumeration<JarEntry> e = jarFile.entries();
		URL[] urls = { new URL("jar:file:" + extensionFile.getPath() + "!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);
		while (e.hasMoreElements()) {
			JarEntry je = e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			String className = je.getName().substring(0, je.getName().length() - 6);
			className = className.replace('/', '.');
			Class<?> temp = cl.loadClass(className);
			if (Converter.class.isAssignableFrom(temp)) {
				loadedClasses[1] = temp;
				temp.getResource("/");
			}
			if (IShape.class.isAssignableFrom(temp)) {
				temp.getResource("/");
				loadedClasses[0] = temp;
			}
		}
		return loadedClasses;
	}
}
