package kr.kro.ezcommand.engine.parser;

import javafx.scene.control.Label;

public class TextLabel implements EZBlockElement {

    private Label ui = new Label();
    public Label getUI() {
        return ui;
    }

    Integer id;
    @Override
    public String getId() {
        return id.toString();
    }

    @Override
    public String toCommand() {
        return null;
    }

    public void setText(String text) {
        ui.setText(text);
    }

    public TextLabel(int cnt,String text) {
        id = cnt;
        setText(text);
        ui.getStyleClass().add("font");
    }

    @Override
    public TextLabel clone() throws CloneNotSupportedException {
        TextLabel label = new TextLabel(id,ui.getText());
        return label;
    }
}

/*Label label;
    public void setText(String str) {
        label.setText(str);
    }
    public Label getUI() {
        return label;
    }
    public TextLabel() {
        label = new Label("");
        label.setStyle("-fx-font-size:10pt; -fx-font-family: \"Jalnan\"");
    }*/