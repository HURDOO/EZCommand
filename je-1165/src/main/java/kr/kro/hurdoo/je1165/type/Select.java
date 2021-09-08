package kr.kro.hurdoo.je1165.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import kr.kro.ezcommand.Main;
import kr.kro.ezcommand.engine.parser.EZBlockElement;

import java.util.ArrayList;
import java.util.List;

public class Select implements EZBlockElement {
    private final String id;
    public final String getId() {
        return id;
    }

    private final ChoiceBox<String> ui = new ChoiceBox<>();
    public ChoiceBox<String> getUI() {
        return ui;
    }

    public final List<String> parseList = new ArrayList<>();

    public void select(int index) {
        ui.getSelectionModel().select(index);
    }

    public Select(String key, JsonObject object) {
        this.object = object;

        String description = object.get("description").getAsString();
        JsonArray list2 = object.get("parse").getAsJsonArray();
        for(JsonElement element : list2) {
            parseList.add(element.getAsString());
        }
        JsonArray list1 = object.get("args").getAsJsonArray();
        int value = Integer.parseInt(object.get("default").getAsString());

        this.id = key;

        for(JsonElement arg : list1)
        {
            ui.getItems().add(arg.getAsString());
        }
        ui.getSelectionModel().select(value);
        ui.getStyleClass().add("font");
        ui.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> resize(newValue));
        ui.setTooltip(new Tooltip(description));
        resize(ui.getItems().get(value));
    }

    private void resize(String value) {
        Platform.runLater(() -> {
            Text text = new Text(value);
            text.setFont(Main.FONT);
            double width = text.getLayoutBounds().getWidth() * 1.5 + 25d;
            ui.setPrefWidth(width);
        });
    }

    public java.lang.String toCommand() {
        return parseList.get(ui.getSelectionModel().getSelectedIndex());
    }

    private final JsonObject object;

    @Override
    public Select clone() {
        Select select = new Select(id,object);
        select.select(ui.getSelectionModel().getSelectedIndex());
        return select;
    }
}
