package kr.kro.hurdoo.je1165.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import kr.kro.ezcommand.engine.parser.EZBlockElement;

public class BooleanLabel implements EZBlockElement {

    private final CheckBox ui = new CheckBox();
    @Override
    public Node getUI() {
        return ui;
    }

    private final String id;
    @Override
    public String getId() {
        return id;
    }

    public void setSelected(boolean bool) {
        ui.setSelected(bool);
    }

    @Override
    public String toCommand() {
        return ui.isSelected() ? parseTrue : parseFalse;
    }

    String parseTrue,parseFalse;

    public BooleanLabel(String id, JsonObject object) {
        this.id = id;
        this.object = object;

        String description = object.get("description").toString();
        ui.setTooltip(new Tooltip(description));

        try {
            JsonArray parseList = object.get("parse").getAsJsonArray();
            parseTrue = parseList.get(0).getAsString();
            parseFalse = parseList.get(1).getAsString();
        } catch (NullPointerException e) {
            parseTrue = "true";
            parseFalse = "false";
        }

        boolean value;
        if(object.get("default") != null) value = object.get("default").getAsBoolean();
        else value = false;
        ui.setSelected(value);

        ui.getStyleClass().add("font");
    }

    private final JsonObject object;

    @Override
    public BooleanLabel clone() {
        BooleanLabel label = new BooleanLabel(id,object);
        label.setSelected(ui.isSelected());
        return label;
    }
}
