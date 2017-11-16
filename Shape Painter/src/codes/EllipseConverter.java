package codes;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;

public class EllipseConverter implements Converter {

    public boolean canConvert(Class cls) {
        return cls.equals(MEllipse.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        MEllipse obj = (MEllipse) value;
        Ellipse temp = (Ellipse) obj.getShape();
        writer.startNode("centerX");
        writer.setValue(new Double(temp.getCenterX()).toString());
        writer.endNode();
        writer.startNode("centerY");
        writer.setValue(new Double(temp.getCenterY()).toString());
        writer.endNode();
        writer.startNode("radiusX");
        writer.setValue(new Double(temp.getRadiusX()).toString());
        writer.endNode();
        writer.startNode("radiusY");
        writer.setValue(new Double(temp.getRadiusY()).toString());
        writer.endNode();
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
    public Object unmarshal(HierarchicalStreamReader reader,
            UnmarshallingContext context) {
        MEllipse loaded = new MEllipse();
        Ellipse temp = (Ellipse) loaded.getShape();
        reader.moveDown();
        temp.setCenterX(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setCenterY(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setRadiusX(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setRadiusY(new Double(reader.getValue()));
        reader.moveUp();
        loaded.setLastCenter(temp.getCenterX(), temp.getCenterY());
        reader.moveDown();
        loaded.setColor(Paint.valueOf(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        loaded.setBorderColor(Paint.valueOf(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        loaded.setBoarderWidth(new Double(reader.getValue()).doubleValue());
        reader.moveUp();
        return loaded;
    }
}
