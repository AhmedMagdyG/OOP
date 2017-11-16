package codes;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Painter extends Application {
	private List<IShape> shapesList = new ArrayList<>();
	private VBox root;
	private Pane canvas;
	private ColorPicker colorPicker;
	private Rectangle fillColor;
	private Rectangle borderColor;
	private ExtensionLoader loader;
	private HBox container;
	private Set<String> visited;
	private SystemFunctions syss;
	private FileChooser fileChooser;
	private Stage stage;
	private Scene scene;
	private UndoRedo doer;
	private SelectionBox selectBox;

	@Override
	public void start(Stage primaryStage) throws Exception {
		loader = new ExtensionLoader(primaryStage);
		stage = primaryStage;
		canvas = new Pane();
		fileChooser = new FileChooser();
		syss = new SystemFunctions();
		visited = new HashSet<>();
		primaryStage.setTitle("Paint");
		root = new VBox();
		doer = new UndoRedo(shapesList, "Operation", canvas);
		scene = new Scene(root, 850, 650);
		scene.getStylesheets().add("styleSheets/style.css");
		addBars();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.DELETE) {
					delete();
				} else if (event.getCode() == KeyCode.ESCAPE) {
					destroySelectionBox();
				}
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(650);
		primaryStage.setMinWidth(850);
		primaryStage.setMaximized(true);
		primaryStage.show();
		SelectionBox.setStage(stage);
		SelectionBox.setCanvas(canvas);
		syss.addConverter(RectangleConverter.class);
		syss.addConverter(LineConverter.class);
		syss.addConverter(EllipseConverter.class);
	}

	private void addBars() {
		root.getChildren().add(setMenuBar());
		root.getChildren().add(setToolsBar());
		canvas.setMinSize(1336, 768);
		canvas.setStyle("-fx-background-color: white;");
		root.getChildren().add(canvas);
	}

	private MenuBar setMenuBar() {
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu());
		return menuBar;
	}

	private GridPane setToolsBar() {
		GridPane toolsBar = new GridPane();
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(20);
		col2.setPercentWidth(35);
		col3.setPercentWidth(45);
		toolsBar.getColumnConstraints().addAll(col1, col2, col3);
		toolsBar.setGridLinesVisible(true);

		Label actions = new Label("Actions");
		Label tools = new Label("Tools");
		Label colors = new Label("Colors");
		toolsBar.add(actions, 0, 1);
		toolsBar.add(tools, 1, 1);
		toolsBar.add(colors, 2, 1);

		toolsBar.add(setActionsSubBar(), 0, 0);
		toolsBar.add(setColorBar(), 2, 0);
		toolsBar.add(setToolsSubBar(), 1, 0);
		return toolsBar;
	}

	private HBox setActionsSubBar() {
		container = new HBox(15);
		container.setId("container");
		Image undoIcon = new Image(getClass().getResourceAsStream("/styleSheets/icons/actions/undo.png"));
		Button undo = new Button("", new ImageView(undoIcon));
		undo.setOnAction(e -> {
			System.out.println("here");
			doer.undo(shapesList);
		});

		Image redoIcon = new Image(getClass().getResourceAsStream("/styleSheets/icons/actions/redo.png"));
		Button redo = new Button("", new ImageView(redoIcon));
		redo.setOnAction(e -> {
			doer.redo(shapesList);
		});

		Image newBoardIcon = new Image(getClass().getResourceAsStream("/styleSheets/icons/actions/newBoard.png"));
		Button newBoard = new Button("", new ImageView(newBoardIcon));
		Button deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(e -> {
			System.out.println("hehe");
			delete();
			destroySelectionBox();
		});
		container.getChildren().addAll(newBoard, deleteBtn, undo, redo);
		return container;
	}

	private void addButtonActionListener(Class<?> cls) {
		Method buttonGetter = null;
		try {
			buttonGetter = cls.getMethod("getShapeButton");
		} catch (NoSuchMethodException | SecurityException e2) {
			e2.printStackTrace();
		}
		Button btn = null;
		try {
			btn = (Button) buttonGetter.invoke(null, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
			e2.printStackTrace();
		}
		btn.setOnMouseClicked(e -> {
			resetActionListeners();
			destroySelectionBox();
			deSelect();
			try {
				draw(cls);
			} catch (InstantiationException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		});
		container.getChildren().add(btn);
	}

	private void resetActionListeners() {
		canvas.setOnMouseClicked(null);
		canvas.setOnMousePressed(null);
		canvas.setOnMouseMoved(null);
		canvas.setOnMouseReleased(null);

	}

	private void passCanvas(Class<?> cls) {
		Method canvasSetter = null;
		try {
			canvasSetter = cls.getMethod("setCanvas", new Class[] { Pane.class });
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		try {
			canvasSetter.invoke(null, canvas);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private HBox setToolsSubBar() {
		container = new HBox(15);
		container.setId("container");
		Image selectIcon = new Image(getClass().getResourceAsStream("/styleSheets/icons/tools/select.png"));
		Button selectBtn = new Button("", new ImageView(selectIcon));
		// Image selectGroupIcon = new Image(getClass().getResourceAsStream(
		// "/styleSheets/icons/tools/selectGroup.png"));
		// Button selectGroupBtn = new Button("", new
		// ImageView(selectGroupIcon));
		Button selectGroupBtn = new Button("Select Group");
		container.getChildren().addAll(selectGroupBtn, selectBtn);
		selectBtn.setOnAction(e -> {
			resetActionListeners();
			selectShapes();
			destroySelectionBox();
		});
		selectGroupBtn.setOnMouseClicked(e -> {
			resetActionListeners();
			destroySelectionBox();
			if (selectBox != null) {
				destroySelectionBox();
			}
			selectBox = new SelectionBox(shapesList);
			selectBox.select(doer);
		});
		addButtonActionListener(MLine.class);
		addButtonActionListener(MRectangle.class);
		addButtonActionListener(MEllipse.class);
		passCanvas(MLine.class);
		passCanvas(MEllipse.class);
		passCanvas(MRectangle.class);
		return container;
	}

	private void destroySelectionBox() {
		if (selectBox != null) {
			selectBox.removeSelectionBox();
			selectBox = null;
		}
	}

	private void selectShapes() {
		Point startPoint = new Point(0, 0);
		canvas.setOnMousePressed(e -> {
			startPoint.setX(e.getX());
			startPoint.setY(e.getY());
			deSelect();
			for (int i = shapesList.size() - 1; i >= 0; i--) {
				if (shapesList.get(i).contains(new Point(e.getX(), e.getY()))) {
					System.out.println("Shape Selected" + shapesList.size());
					shapesList.get(i).setSelected(true);
					break;
				}
			}
		});
		canvas.setOnMouseDragged(e -> {
			move(new Point(e.getX() - startPoint.getX(), e.getY() - startPoint.getY()));
		});
		canvas.setOnMouseReleased(e -> {
			for (int i = 0; i < shapesList.size(); i++) {
				IShape temp = shapesList.get(i);
				if (temp.isSelected()) {
					temp.resetOrigin();
					temp.setSelected(false);
					canvas.getChildren().remove(temp.getShape());
					shapesList.set(i, temp.clone());
					canvas.getChildren().add(i,shapesList.get(i).getShape());
					doer.addEntry(temp,i,"Operation");
				}
			}
		});
	}
	
	private void makeHistoryEntry() {
		System.out.println("eh elhabal dah");
		List<IShape> shapes = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < shapesList.size(); i++) {
			IShape temp = shapesList.get(i);
			if (temp.isSelected()) {
				temp.resetOrigin();
				temp.setSelected(false);
				canvas.getChildren().remove(temp.getShape());
				shapesList.set(i, temp.clone());
				canvas.getChildren().add(i,shapesList.get(i).getShape());
				shapes.add(shapesList.get(i));
				indices.add(i);
			}
		}
		doer.addEntry(shapes,indices,"Operation");
	}

	private void deSelect() {
		for (IShape sh : shapesList) {
			sh.setSelected(false);
		}
	}

	private void changeSelectedShapesColors() {
		for (IShape x : shapesList) {
			if (x.isSelected()) {
				x.setBorderColor(borderColor.getFill());
				x.setColor(fillColor.getFill());
			}
		}
		makeHistoryEntry();
		
	}

	private HBox setColorBar() {
		HBox bar = new HBox();
		colorPicker = new ColorPicker();
		ListView<Rectangle> shapeColors;
		fillColor = new Rectangle(80, 12);
		borderColor = new Rectangle(80, 12);
		ObservableList<Rectangle> items = FXCollections.observableArrayList(fillColor, borderColor);
		shapeColors = new ListView<Rectangle>(items);
		colorPicker.setOnAction(e -> {
			shapeColors.getSelectionModel().getSelectedItem().setFill(colorPicker.getValue());
			changeSelectedShapesColors();
			deSelect();
			destroySelectionBox();
		});
		shapeColors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rectangle>() {
			@Override
			public void changed(ObservableValue<? extends Rectangle> observable, Rectangle oldValue,
					Rectangle newValue) {
				colorPicker.setValue((Color) newValue.getFill());
			}
		});
		fillColor.setFill(Color.TRANSPARENT);
		shapeColors.getSelectionModel().select(0);
		bar.getChildren().addAll(shapeColors, colorPicker);
		Slider boarderWidth = new Slider();
		boarderWidth.setMin(0);
		boarderWidth.setMax(10);
		boarderWidth.setValue(5);
		boarderWidth.setShowTickLabels(true);
		boarderWidth.setShowTickMarks(true);
		boarderWidth.setMajorTickUnit(5);
		boarderWidth.setMinorTickCount(1);
		boarderWidth.setBlockIncrement(1);
		boarderWidth.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				for (IShape x : shapesList) {
					if (x.isSelected()) {
						x.setBoarderWidth(new_val.doubleValue());
					}
				}
			}
		});
		bar.getChildren().add(boarderWidth);
		return bar;
	}

	private Menu fileMenu() {
		Menu temp = new Menu("File");
		MenuItem openImage = new MenuItem("Open Image");
		MenuItem newBoard = new MenuItem("New");
		// MenuItem save = new MenuItem("Save");
		Menu save = new Menu("Save");
		MenuItem saveXML = new MenuItem("Save as XML");
		
		saveXML.setOnAction(e -> {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml");
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().removeAll();
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(stage);
			syss.saveXML(file, shapesList);
		});
		MenuItem saveJSON = new MenuItem("Save as JSON");
		saveJSON.setOnAction(e -> {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON (*.json)", "*.json");
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().removeAll();
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(stage);
			syss.saveJSON(file, shapesList);
		});
		MenuItem load = new MenuItem("Load");
		load.setOnAction(e -> {
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				shapesList = syss.load(file, shapesList,doer,canvas);
				canvas.getChildren().removeAll();
				for (int i = 0; i < shapesList.size(); i++) {
					canvas.getChildren().add(shapesList.get(i).getShape());
				}
			}
		});
		save.getItems().addAll(saveXML, saveJSON);
		MenuItem loadExtension = new MenuItem("Load Extension");
		MenuItem exit = new MenuItem("Exit");
		MenuItem about = new MenuItem("About");
		temp.getItems().addAll(newBoard, openImage, save, load, loadExtension, exit, about);
		loadExtension.setOnAction(e -> {
			fileChooser.setTitle("Open Resource File");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JAR (*.jar)", "*.jar");
			fileChooser.getExtensionFilters().add(extFilter);
			File extensionFile = fileChooser.showOpenDialog(stage);
			if (extensionFile != null) {
				try {
					Class<?>[] loadedClass = loader.load(extensionFile);
					if (visited.contains(loadedClass[0].getName())) {
						throw new RuntimeException("Extension added before\n");
					}
					visited.add(loadedClass[0].getName());
					passCanvas(loadedClass[0]);
					addButtonActionListener(loadedClass[0]);
					syss.addConverter(loadedClass[1]);
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Error While Loading Extension");
					alert.setContentText(
							"Error happened\nPlease choose a valid extension.\n" + "Make sure it's not loaded before.");
					alert.show();
				}
			}
		});
		return temp;

	}

	private void move(Point p) {
		for (IShape x : shapesList) {
			if (x.isSelected()) {
				x.move(p);
			}
		}
	}

	private void draw(Class<?> cls) throws InstantiationException, IllegalAccessException {
		IShape temp = (IShape) cls.newInstance();
		temp.draw(shapesList, (Color) fillColor.getFill(), borderColor.getFill(), doer);
	}

	private void delete() {
		List<IShape> shapes = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();
		for (int i = shapesList.size() - 1; i >= 0; i--) {
			if (shapesList.get(i).isSelected()) {
				shapes.add(0, shapesList.get(i).clone());
				indices.add(0, i);
				canvas.getChildren().remove(shapesList.get(i).getShape());
				shapesList.remove(i);
			}
		}
		destroySelectionBox();
		if (shapes.size() != 0) {
			doer.addEntry(shapes, indices, "Delete");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}