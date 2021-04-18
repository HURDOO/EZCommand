package kr.kro.ezcommand.engine.parser.type;

import javafx.scene.control.Label;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;

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

    public TextLabel(EZBlock block,int cnt,String text) {
        id = cnt;
        parent = block;
        setText(text);
        ui.getStyleClass().add("font");
    }

    private EZBlock parent;
    public void setEZBlock(EZBlock parent) {
        this.parent = parent;
    }
    @Override
    public EZBlock getParent() {
        return parent;
    }

    @Override
    public TextLabel clone(EZBlock block) throws CloneNotSupportedException {
        TextLabel label = new TextLabel(parent,id,ui.getText());
        label.parent = block;
        label.setEZBlock(parent);
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