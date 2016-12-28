package shapes;

import java.lang.reflect.Constructor;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TryMain extends Application {
	private static DynamicLoader x = new DynamicLoader();
	private static ShapeGenerator y = new ShapeGenerator();
	public static void main(String[] args) {
		Constructor<?>[] z = x.loadClass("/home/moamen/SquareShape.jar");
		ShapeFactory.addNewShape(z);
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Group group = new Group();
		
		 CustomShape sq = y.getShape();
		 System.out.println(sq);
		 sq.setXPostion(20);
		 sq.setYPostion(20);
		 group.getChildren().add(sq.getShape());

		CustomShape rect = y.getShape();
		System.out.println(rect);
		rect.setXPostion(220);
		rect.setYPostion(220);
		group.getChildren().add(rect.getShape());

		Scene scene = new Scene(group, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}