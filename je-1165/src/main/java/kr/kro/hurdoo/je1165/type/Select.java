package kr.kro.hurdoo.je1165.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import kr.kro.ezcommand.Main;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;

import java.util.ArrayList;
import java.util.List;

public class Select implements EZBlockElement {
    private java.lang.String id;
    public java.lang.String getId() {
        return id;
    }

    private ChoiceBox ui = new ChoiceBox();
    public ChoiceBox getUI() {
        return ui;
    }

    private List<String> parseList = new ArrayList<>();
    public List<String> getParseList() {
        return parseList;
    }

    private EZElementContainer parent;
    public EZElementContainer getParent() {
        return parent;
    }

    public void select(int index) {
        ui.getSelectionModel().select(index);
    }

    public Select(EZElementContainer container,String key, JsonObject object) {
        this.object = object;
        parent = container;

        String description = object.get("description").getAsString();;
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
        ui.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            resize(newValue.toString());
        });
        ui.setTooltip(new Tooltip(description));
        resize(ui.getItems().get(value).toString());
    }

    private void resize(String value) {
        Platform.runLater(() -> {
            Text text = new Text(value);
            text.setFont(Main.FONT);
            double width = text.getLayoutBounds().getWidth() * 1.5 + 25d;
            ui.setPrefWidth(width);

            parent.resize();
        });
    }

    public java.lang.String toCommand() {
        if(parseList == null) return ui.getSelectionModel().getSelectedItem().toString();
        return parseList.get(ui.getSelectionModel().getSelectedIndex());
    }

    private JsonObject object;

    @Override
    public Select clone(EZBlock block) {
        Select select = new Select(parent,id,object);
        select.parent = block;
        select.select(ui.getSelectionModel().getSelectedIndex());
        return select;
    }
}
