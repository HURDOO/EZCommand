package kr.kro.ezcommand.engine.parser.type;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BooleanLabel implements EZBlockElement {

    private CheckBox ui = new CheckBox();
    @Override
    public Node getUI() {
        return ui;
    }

    private String id;
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toCommand() {
        return ui.isSelected() ? parseTrue : parseFalse;
    }

    String parseTrue,parseFalse;

    public BooleanLabel(String id, JSONObject object) {
        this.id = id;

        String description = object.get("description").toString();
        ui.setTooltip(new Tooltip(description));

        try {
            JSONArray parseList = (JSONArray) object.get("parse");
            parseTrue = parseList.get(0).toString();
            parseFalse = parseList.get(1).toString();
        } catch (NullPointerException e) {
            parseTrue = "true";
            parseFalse = "false";
        }

        boolean value = (boolean) object.getOrDefault("default",false);
        ui.setSelected(value);

    }
}
