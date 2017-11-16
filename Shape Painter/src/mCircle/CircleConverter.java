package mCircle;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;

public class CircleConverter implements Converter {

    public boolean canConvert(Class cls) {
        return cls.equals(MCircle.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        MCircle obj = (MCircle) value;
        Ellipse temp = (Ellipse) obj.getShape();
        writer.startNode("centerX");
        writer.setValue(new Double(temp.getCenterX()).toString());
        writer.endNode();
        writer.startNode("centerY");
        writer.setValue(new Double(temp.getCenterY()).toString());
        writer.endNode();
        writer.startNode("radius");
        writer.setValue(new Double(temp.getRadiusX()).toString());
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
        MCircle loaded = new MCircle();
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
        temp.setRadiusY(temp.getRadiusX());
        reader.moveDown();
        loaded.setColor(Paint.valueOf(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        loaded.setBorderColor(Paint.valueOf(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        loaded.setBoarderWidth(new Double(reader.getValue()).doubleValue());
        reader.moveUp();
        loaded.setLastCenter(temp.getCenterX(), temp.getCenterY());
        return loaded;
    }
}
