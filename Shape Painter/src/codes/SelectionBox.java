package codes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SelectionBox {
    private Rectangle box;
    private static Pane canvas;
    private static Stage stage;
    private List<IShape> shapesList;
    private List<IShape> selected;
    private List<Integer> selectedIndex;
    private Point corner;
    private Corner topLeft;
    private Corner topRight;
    private Corner lowerLeft;
    private Corner lowerRight;
    private Point boxScale;
    private Point originalDimensions;
    private Point startPoint;
    private Point lastBoxCorner;
    private UndoRedo doer;

    public SelectionBox(List<IShape> shapesList) {
        box = new Rectangle();
        boxScale = new Point(1, 1);
        selected = new ArrayList<>();
        selectedIndex = new ArrayList<>();
        startPoint = new Point();
        lastBoxCorner = new Point();
        this.shapesList = shapesList;
        setBox();
    }

    private void setBox() {
        box.setFill(Color.TRANSPARENT);
        box.setStroke(Color.DARKBLUE);
        box.getStrokeDashArray().addAll(5d, 5d);
        topLeft = new Corner();
        topRight = new Corner();
        lowerRight = new Corner();
        lowerLeft = new Corner();
    }

    private void setCornersPositions(double x, double y, double width,
            double height) {
        topLeft.setPosition(new Point(x, y));
        topRight.setPosition(new Point(x + width, y));
        lowerRight.setPosition(new Point(x + width, y + height));
        lowerLeft.setPosition(new Point(x, y + height));
    }

    public static void setCanvas(Pane canvas) {
        SelectionBox.canvas = canvas;
    }

    public static void setStage(Stage stage) {
        SelectionBox.stage = stage;
    }

    public void select(UndoRedo doer) {
    	this.doer = doer;
        canvas.setOnMouseReleased(e -> {
            canvas.setOnMouseDragged(null);
            canvas.setOnMousePressed(null);
            canvas.setOnMouseReleased(null);
            topLeft.resetOrigin();
            topRight.resetOrigin();
            lowerLeft.resetOrigin();
            lowerRight.resetOrigin();
            getSelectedShapes();
            setCorners(topLeft);
            setCorners(topRight);
            setCorners(lowerLeft);
            setCorners(lowerRight);
            originalDimensions = new Point(box.getWidth(), box.getHeight());
            lastBoxCorner.setPoint(box.getX(), box.getY());
            moveBox();
        });
        canvas.setOnMousePressed(e -> {
            System.out.println("Selection Box Started");
            canvas.getChildren().add(box);
            canvas.getChildren().add(topLeft.getShape());
            canvas.getChildren().add(topRight.getShape());
            canvas.getChildren().add(lowerLeft.getShape());
            canvas.getChildren().add(lowerRight.getShape());
            corner = new Point(e.getX(), e.getY());
            box.setX(e.getX());
            box.setY(e.getY());
            topLeft.setPosition(new Point(e.getX(), e.getY()));
            setCornersPositions(e.getX(), e.getY(), 0, 0);
        });
        canvas.setOnMouseDragged(e -> {
            if (e.getX() < corner.getX()) {
                box.setWidth(corner.getX() - e.getX());
                box.setX(e.getX());
            } else {
                box.setX(corner.getX());
                box.setWidth(e.getX() - corner.getX());
            }
            if (e.getY() < corner.getY()) {
                box.setHeight(corner.getY() - e.getY());
                box.setY(e.getY());
            } else {
                box.setHeight(e.getY() - corner.getY());
                box.setY(corner.getY());
            }
            setCornersPositions(box.getX(), box.getY(), box.getWidth(), box
                    .getHeight());
        });
    }

    private void getSelectedShapes() {
        System.out.println(shapesList.size());
        selected = new ArrayList<IShape>();
        selectedIndex = new ArrayList<Integer>();
        for (int i = 0; i < shapesList.size(); i++) {
            if (shapesList.get(i).getMinX() >= box.getX() && shapesList.get(i)
                    .getMinY() >= box
                            .getY()
                    && shapesList.get(i).getMaxX() <= box.getX() + box
                            .getWidth() && shapesList.get(i)
                                    .getMaxY() <= box.getY() + box
                                            .getHeight()) {
                selected.add(shapesList.get(i));
                selectedIndex.add(i);
                shapesList.get(i).setSelected(true);
            }
        }
        System.out.println("Sellected Size ----------> " + selected.size());
    }

    private void setCorners(Corner corn) {
        corn.getShape().setOnMouseEntered(e -> {
            if (corn.equals(topLeft)) {
                stage.getScene().setCursor(Cursor.NW_RESIZE);
            } else if (corn.equals(topRight)) {
                stage.getScene().setCursor(Cursor.NE_RESIZE);
            } else if (corn.equals(lowerLeft)) {
                stage.getScene().setCursor(Cursor.SW_RESIZE);
            } else if (corn.equals(lowerRight)) {
                stage.getScene().setCursor(Cursor.SE_RESIZE);
            }
        });
        corn.getShape().setOnMouseExited(e -> {
            if (corn.equals(topLeft)) {
                stage.getScene().setCursor(Cursor.DEFAULT);
            } else if (corn.equals(topRight)) {
                stage.getScene().setCursor(Cursor.DEFAULT);
            } else if (corn.equals(lowerLeft)) {
                stage.getScene().setCursor(Cursor.DEFAULT);
            } else if (corn.equals(lowerRight)) {
                stage.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        corn.getShape().setOnMousePressed(e -> {
            moveCorner(corn, new Point(corn.getShape().getX(), corn.getShape()
                    .getY()));
        });
    }

    private void bindBox() {
        box.setX(topLeft.getBoxPoint().getX());
        box.setY(topLeft.getBoxPoint().getY());
        box.setWidth(topRight.getBoxPoint().getX() - topLeft
                .getBoxPoint().getX());
        box.setHeight(lowerRight.getBoxPoint().getY() - topRight
                .getBoxPoint().getY());
        boxScale.setPoint(box.getWidth() / originalDimensions.getX(), box
                .getHeight() / originalDimensions.getY());
    }

    private void scaleShapes() {
        for (IShape x : selected) {
            x.resize(boxScale);
        }
    }

    private void moveShapes(Point p) {
        for (IShape x : selected) {
            x.move(p);
        }
    }

    private void moveBox() {
        box.setOnMousePressed(e -> {
            startPoint.setPoint(e.getX(), e.getY());
        });
        box.setOnMouseDragged(e -> {
            box.setX(lastBoxCorner.getX() + e.getX() - startPoint
                    .getX());
            box.setY(lastBoxCorner.getY() + e.getY() - startPoint
                    .getY());
            setCornersPositions(box.getX(), box.getY(), box.getWidth(), box
                    .getHeight());
            moveShapes(new Point(e.getX() - startPoint.getX(), e.getY()
                    - startPoint.getY()));
            topLeft.resetOrigin();
            topRight.resetOrigin();
            lowerLeft.resetOrigin();
            lowerRight.resetOrigin();
        });
        box.setOnMouseReleased(e -> {
            lastBoxCorner.setPoint(box.getX(), box.getY());
            for (int i = 0;i<selected.size();i++) {
                IShape x = selected.get(i);
                if(x.isSelected()) {
	            	x.resetOrigin();
	                canvas.getChildren().remove(x.getShape());
	                selected.set(i,x.clone());
	                canvas.getChildren().add(selectedIndex.get(i),x.getShape());
                }
            }
            doer.addEntry(selected,selectedIndex,"Operaion");
        });
    }

    private void moveCorner(Corner corn, Point p) {
        corn.setLastCorner(p);
        corn.getShape().setOnMouseDragged(e -> {
            Point vec = new Point(e.getX() - corn.getLastCorner().getX(), e
                    .getY()
                    - corn.getLastCorner().getY());
            corn.move(vec);
            if (corn.equals(topLeft)) {
                lowerLeft.move(new Point(vec.getX(), -1 * vec.getY()));
                lowerRight.move(new Point(-1 * vec.getX(), -1 * vec
                        .getY()));
                topRight.move(new Point(-1 * vec.getX(), vec.getY()));
            } else if (corn.equals(topRight)) {
                topLeft.move(new Point(-1 * vec.getX(), vec.getY()));
                lowerLeft.move(new Point(-1 * vec.getX(), -1 * vec.getY()));
                lowerRight.move(new Point(vec.getX(), -1 * vec.getY()));
            } else if (corn.equals(lowerLeft)) {
                topLeft.move(new Point(vec.getX(), -1 * vec.getY()));
                topRight.move(new Point(-1 * vec.getX(), -1 * vec.getY()));
                lowerRight.move(new Point(-1 * vec.getX(), vec.getY()));
            } else if (corn.equals(lowerRight)) {
                topLeft.move(new Point(-1 * vec.getX(), -1 * vec.getY()));
                topRight.move(new Point(vec.getX(), -1 * vec.getY()));
                lowerLeft.move(new Point(-1 * vec.getX(), vec.getY()));
            }
            bindBox();
            scaleShapes();
        });
        corn.getShape().setOnMouseReleased(e -> {
            lastBoxCorner.setPoint(box.getX(), box.getY());
            topLeft.resetOrigin();
            topRight.resetOrigin();
            lowerLeft.resetOrigin();
            lowerRight.resetOrigin();
            for (int i = 0;i<selected.size();i++) {
                IShape x = selected.get(i);
                if(x.isSelected()) {
	            	x.resetOrigin();
	                canvas.getChildren().remove(x.getShape());
	                selected.set(i,x.clone());
	                canvas.getChildren().add(selectedIndex.get(i),x.getShape());
                }
            }
        });
    }

    public void removeSelectionBox() {
        canvas.getChildren().remove(box);
        canvas.getChildren().remove(topLeft.getShape());
        canvas.getChildren().remove(topRight.getShape());
        canvas.getChildren().remove(lowerLeft.getShape());
        canvas.getChildren().remove(lowerRight.getShape());
    }

    public boolean contain(Point p) {
        return box.contains(p.getX(), p.getY());
    }
}