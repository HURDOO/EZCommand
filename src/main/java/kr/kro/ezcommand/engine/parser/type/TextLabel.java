package kr.kro.ezcommand.engine.parser.type;

import javafx.scene.control.Label;
import kr.kro.ezcommand.engine.parser.EZBlockElement;

public class TextLabel extends Label implements EZBlockElement {
    public Label getUI() {
        return this;
    }

    @Override
    public String toCommand() {
        return null;
    }

    public TextLabel() {
        super("");
        super.setStyle("-fx-font-size:10pt; -fx-font-family: \"Jalnan\"");
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