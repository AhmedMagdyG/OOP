package mSquare;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SquareConverter implements Converter {

    @Override
    public boolean canConvert(Class cls) {
        return cls.equals(MSquare.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        MSquare obj = (MSquare) value;
        writer.startNode("topLeftCornerX");
        Rectangle temp = (Rectangle) obj.getShape();
        writer.setValue(new Double(temp.getX()).toString());
        writer.endNode();
        writer.startNode("topLeftCornerY");
        writer.setValue(new Double(temp.getY()).toString());
        writer.endNode();
        writer.startNode("side");
        writer.setValue(new Double(temp.getHeight()).toString());
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
        MSquare loaded = new MSquare();
        Rectangle temp = (Rectangle) loaded.getShape();
        reader.moveDown();
        temp.setX(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setY(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setHeight(new Double(reader.getValue()));
        reader.moveUp();
        temp.setWidth(temp.getHeight());
        loaded.resetOrigin();
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
