package kr.kro.ezcommand.engine.parser.type;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Number implements EZBlockElement {
    public java.lang.String getId() {
        return id;
    }

    private java.lang.String id;

    public TextField getUI() {
        return ui;
    }

    private TextField ui = new TextField();

    private PseudoClass incorrect = PseudoClass.getPseudoClass("incorrect");

    private Long min_long, max_long, value_long;
    private Double min_double, max_double, value_double;

    boolean isDouble;

    public Number(String key, JSONObject object) {

        String description = object.get("description").toString();
        JSONArray numList = (JSONArray) object.get("range");
        String min = parseMinMax(numList.get(0).toString());
        String max = parseMinMax(numList.get(1).toString());

        switch (object.get("format").toString())
        {
            case "byte":
            case "short":
            case "int":
            case "long":
            case "integer":
                isDouble = false;
                min_long = Long.parseLong(min);
                max_long = Long.parseLong(max);
                try {
                    value_long = Long.parseLong(object.get("default").toString());
                } catch (NullPointerException ignored) {}
                break;

            case "float":
            case "double":
            case "rational":
                isDouble = true;
                min_double = Double.parseDouble(min);
                max_double = Double.parseDouble(max);
                try {
                    value_double = Double.parseDouble(object.get("default").toString());
                } catch (NullPointerException ignored) {}
                break;

            default:
                throw new IllegalArgumentException("Number Format is: " + object.get("format").toString());
        }

        this.id = key;

        ui.setText(isDouble ? value_double.toString() : value_long.toString());
        ui.getStylesheets().add("/src/main/resources/css/ErrorableTextField.css");
        ui.getStyleClass().add("text");
        ui.getStyleClass().add("color");

        ui.textProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                if(isDouble) value_double = parseDouble(newValue);
                else value_long = parseLong(newValue);
                ui.pseudoClassStateChanged(incorrect, false);
            } catch (IllegalArgumentException e) {
                // this catches NumberFormatException
                ui.pseudoClassStateChanged(incorrect, true);
            }
            resize();
        }));
        ui.setTooltip(new Tooltip("숫자 (" + min + "~" + max + ")\n\n" + description));
        resize();
    }

    private void resize()
    {
        Platform.runLater(() -> {
            Text text = new Text(ui.getText());
            text.setFont(ui.getFont()); // Set the same font, so the size is the same
            double width = text.getLayoutBounds().getWidth() // This big is the Text in the TextField
                    + ui.getPadding().getLeft() + ui.getPadding().getRight() // Add the padding of the TextField
                    + 2d; // Add some spacing
            ui.setPrefWidth(width); // Set the width
            ui.positionCaret(ui.getCaretPosition()); // If you remove this line, it flashes a little bit
        });

        // https://stackoverflow.com/questions/12737829/javafx-textfield-resize-to-text-length
    }

    private Double parseDouble(String s) throws IllegalArgumentException,NumberFormatException {
        double num = Double.parseDouble(s);
        if(num < min_double || num > max_double)
            throw new IllegalArgumentException();
        return num;
    }

    private Long parseLong(String s) throws IllegalArgumentException,NumberFormatException {
        long num = Long.parseLong(s);
        if(num < min_long || num > max_long)
            throw new IllegalArgumentException();
        return num;
    }

    public java.lang.String getValue() throws IllegalArgumentException {
        if(isDouble) return value_double.toString();
        else return value_long.toString();
    }

    public String toCommand() {
        try {
            return getValue();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Exception";
        }
    }

    public String parseMinMax(String s) {
        switch (s)
        {
            case "BYTE_MIN":
                s = Byte.toString(Byte.MIN_VALUE);
                break;
            case "BYTE_MAX":
                s = Byte.toString(Byte.MAX_VALUE);
            case "SHORT_MIN":
                s = Short.toString(Short.MIN_VALUE);
                break;
            case "SHORT_MAX":
                s = Short.toString(Short.MAX_VALUE);
                break;
            case "INT_MIN":
            case "INTEGER_MIN":
                s = Integer.toString(Integer.MIN_VALUE);
                break;
            case "INT_MAX":
            case "INTEGER_MAX":
                s = Integer.toString(Integer.MAX_VALUE);
                break;
            case "LONG_MIN":
                s = Long.toString(Long.MIN_VALUE);
                break;
            case "LONG_MAX":
                s = Long.toString(Long.MAX_VALUE);
                break;
            case "FLOAT_MIN":
                s = Float.toString(Float.MIN_VALUE);
                break;
            case "FLOAT_MAX":
                s = Float.toString(Float.MAX_VALUE);
                break;
            case "DOUBLE_MIN":
            case "RATIONAL_MIN":
                s = Double.toString(Double.MIN_VALUE);
                break;
            case "DOUBLE_MAX":
            case "RATIONAL_MAX":
                s = Double.toString(Double.MAX_VALUE);
                break;
            default:
                break;
        }
        return s;
    }
}
