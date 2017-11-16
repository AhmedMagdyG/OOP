package mTriangle;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class TriangleConverter implements Converter {

	@Override
	public boolean canConvert(Class cls) {
		return cls.equals(MTriangle.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		MTriangle obj = (MTriangle) value;
		Polygon temp = (Polygon) obj.getShape();
		for (int i = 0; i < temp.getPoints().size(); i++) {
			writer.startNode("Point" + (i / 2 + 1) + "x");
			writer.setValue(temp.getPoints().get(i).toString());
			writer.endNode();
			writer.startNode("Point" + (i / 2 + 1) + "y");
			writer.setValue(temp.getPoints().get(i + 1).toString());
			writer.endNode();
			i++;
		}
		writer.startNode("fillColor");
		writer.setValue(obj.getColor().toString());
		writer.endNode();
		writer.startNode("borderColor");
		writer.setValue(obj.getBorderColor().toString());
		writer.endNode();
		writer.startNode("borderWidth");
		writer.setValue(new Double(obj.getBoarderWidth()).toString());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		MTriangle loaded = new MTriangle();
		Polygon temp = (Polygon) loaded.getShape();
		for (int i = 0; i < 6; i++) {
			reader.moveDown();
			temp.getPoints().add(Double.valueOf(reader.getValue()));
			reader.moveUp();
		}
		reader.moveDown();
		loaded.setColor(Paint.valueOf(reader.getValue()));
		reader.moveUp();
		reader.moveDown();
		loaded.setBorderColor(Paint.valueOf(reader.getValue()));
		reader.moveUp();
		reader.moveDown();
		loaded.setBoarderWidth(new Double(reader.getValue()).doubleValue());
		reader.moveUp();
		loaded.resetOrigin();
		return loaded;
	}
}
