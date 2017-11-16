package codes;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class LineConverter implements Converter {

    @Override
    public boolean canConvert(Class cls) {
        return cls.equals(MLine.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
        MLine obj = (MLine) value;
        writer.startNode("startPointX");
        Line temp = (Line) obj.getShape();
        writer.setValue(new Double(temp.getStartX()).toString());
        writer.endNode();
        writer.startNode("startPointY");
        writer.setValue(new Double(temp.getStartY()).toString());
        writer.endNode();
        writer.startNode("endPointX");
        writer.setValue(new Double(temp.getEndX()).toString());
        writer.endNode();
        writer.startNode("endPointY");
        writer.setValue(new Double(temp.getEndY()).toString());
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
        MLine loaded = new MLine();
        reader.moveDown();
        Line temp = (Line) loaded.getShape();
        temp.setStartX(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setStartY(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setEndX(new Double(reader.getValue()));
        reader.moveUp();
        reader.moveDown();
        temp.setEndY(new Double(reader.getValue()));
        reader.moveUp();
        loaded.setLastLineStart(temp.getStartX(), temp.getStartY());
        loaded.setLastLineEnd(temp.getEndX(), temp.getEndY());
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
