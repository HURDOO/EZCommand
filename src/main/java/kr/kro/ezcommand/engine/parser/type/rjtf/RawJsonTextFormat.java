package kr.kro.ezcommand.engine.parser.type.rjtf;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.ui.fxml.RJTFTextEditorController;
import kr.kro.ezcommand.ui.stage.RJTFStage;
import org.controlsfx.control.PopOver;
import org.fxmisc.richtext.InlineCssTextArea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RawJsonTextFormat implements EZBlockElement {

    private Button ui = new Button("");

    public List<JsonText> getContent() {
        return content;
    }
    private List<JsonText> content = new ArrayList<>();

    private String id;
    private boolean useSmallQuote;

    public RawJsonTextFormat(EZBlock block,String name, JSONObject object) throws IOException {
        id = name;
        parent = block;
        this.object = object;

        String description = object.get("description").toString();
        String value = object.get("default").toString();

        try {
            useSmallQuote = (boolean) object.get("useSmallQuote");
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
        JSONArray array = new JSONArray();
        for(JsonText text : content) {
            array.add(text.toJson());
        }
        return array.toJSONString();
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
        Platform.runLater(() -> {
            parent.resize();
        });
    }

    private EZBlock parent;
    @Override
    public EZBlock getEZBlock() {
        return parent;
    }

    private JSONObject object;
    public RawJsonTextFormat clone(EZBlock block) {
        RawJsonTextFormat clone;
        try {
            clone = new RawJsonTextFormat(parent,id,object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        clone.parent = parent;
        clone.content.clear();
        for(JsonText text : content) {
            clone.content.add(text.clone());
        }
        clone.updateText();
        return clone;
    }
}

