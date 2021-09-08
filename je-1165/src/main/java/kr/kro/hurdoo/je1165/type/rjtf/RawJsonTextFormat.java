package kr.kro.hurdoo.je1165.type.rjtf;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import kr.kro.ezcommand.engine.parser.EZBlockElement;

import java.util.ArrayList;
import java.util.List;

public class RawJsonTextFormat implements EZBlockElement {

    private final Button ui = new Button("");

    public List<JsonText> getContent() {
        return content;
    }
    private final List<JsonText> content = new ArrayList<>();

    private final String id;
    private boolean useSmallQuote;

    public RawJsonTextFormat(String name, JsonObject object) {
        id = name;
        this.object = object;

        String description = object.get("description").getAsString();
        ui.setTooltip(new Tooltip(description));
        String value = object.get("default").getAsString();

        try {
            useSmallQuote = object.get("useSmallQuote").getAsBoolean();
        } catch (NullPointerException e) {
            useSmallQuote = false;
        }
        JsonText text1 = new JsonText();
        text1.setText(value);
        content.add(text1);

        updateText();

        ui.setOnAction(event -> {
            RJTFStage.getController().register(this);
            RJTFStage.show();
        });
        ui.setStyle("-fx-font-family: \"NanumBarunGothic\"");
    }

    @Override
    public Button getUI() {
        return ui;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toCommand() {
        if(useSmallQuote) return "'" + toJsonString() + "'";
        return toJsonString();
    }

    public String toJsonString() {
        if(content.isEmpty()) return "\"\"";
        if(content.size() == 1) {
            return content.get(0).toJsonString();
        }
        JsonArray array = new JsonArray();
        for(JsonText text : content) {
            array.add(text.toJson());
        }
        return array.toString();
    }

    public void updateText() {
        StringBuilder builder = new StringBuilder();
        for(JsonText text : content) {
            builder.append(text.getText());
        }
        if(builder.length() > 10)
        {
            builder.setLength(10);
            builder.append("...");
        }
        ui.setText(builder.toString());
        // resize
    }
    private final JsonObject object;
    public RawJsonTextFormat clone() {
        RawJsonTextFormat clone;
        clone = new RawJsonTextFormat(id,object);
        clone.content.clear();
        for(JsonText text : content) {
            clone.content.add(text.clone());
        }
        clone.updateText();
        return clone;
    }
}

