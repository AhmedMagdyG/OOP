package codes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class SystemFunctions {
	Scanner sc;
	FileWriter writer;
	XStream streamXML;
	XStream streamJSON;

	public SystemFunctions() {
		streamXML = new XStream(new DomDriver());
		streamJSON = new XStream(new JettisonMappedXmlDriver());
		streamJSON.setMode(XStream.NO_REFERENCES);
	}

	public void addConverter(Class<?> cls) {
		System.out.println("here");
		try {
			streamXML.registerConverter((Converter) cls.newInstance());
			streamJSON.registerConverter((Converter) cls.newInstance());
		} catch (Exception e) {

		}
	}

	public void saveXML(File f, List<IShape> shapesList) {
		if (f != null) {
			try {
				f.createNewFile();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
				String content = streamXML.toXML(shapesList);
				writer.write(content);
				writer.close();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("LOAD FAILED!");
				alert.setHeaderText("Failed to load data!");
				alert.setContentText("A problem occured during loading this file.\n"
						+ "File may be corrupted or has an invalid extension");
				alert.showAndWait();
				e.printStackTrace();
			}
		}
	}

	public void saveJSON(File f, List<IShape> shapesList) {
		if (f != null) {
			try {
				f.createNewFile();
				writer = new FileWriter(f);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
				String content = streamJSON.toXML(shapesList);
				writer.write(content);
				writer.close();
				System.out.println("Done");
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("LOAD FAILED!");
				alert.setHeaderText("Failed to load data!");
				alert.setContentText("A problem occured during loading this file.\n"
						+ "File may be corrupted or has an invalid extension");
				alert.showAndWait();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public List<IShape> load(File f, List<IShape> shapesList, UndoRedo doer, Pane canvas) {
		List<IShape> loadedList = new ArrayList<>();
		try {
			Scanner sc = new Scanner(f);
			String fileContent = sc.useDelimiter("\\Z").next();
			if (f.getName().endsWith(".xml")) {
				loadedList = (List<IShape>) streamXML.fromXML(fileContent);
			} else if (f.getName().endsWith(".json")) {
				loadedList = (List<IShape>) streamJSON.fromXML(fileContent);
			} else {
				throw new RuntimeException();
			}
			doer.resetRedo();
			doer.resetUndo();
			doer.addEntry(loadedList, "Create");
			sc.close();
			for (int i = 0; i < shapesList.size(); i++) {
				canvas.getChildren().remove(shapesList.get(i).getShape());
			}
			return loadedList;
		} catch (Exception e) {
			System.out.println("here");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("LOAD FAILED!");
			alert.setHeaderText("Failed to load data!");
			alert.setContentText("There're some missing extensions.\n" + "Please, load all needed extensions");
			alert.showAndWait();
		}
		return shapesList;
	}

}
