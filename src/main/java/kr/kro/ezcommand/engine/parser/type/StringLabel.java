package kr.kro.ezcommand.engine.parser.type;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;
import org.json.simple.JSONObject;

public class StringLabel implements EZBlockElement {

    private TextField ui = new TextField();
    @Override
    public TextField getUI() {
        return ui;
    }

    private String id;
    @Override
    public String getId() {
        return id;
    }

    private EZElementContainer parent;
    @Override
    public EZElementContainer getParent() {
        return parent;
    }

    @Override
    public String toCommand() {
        return ui.textProperty().getValue();
    }

    private PseudoClass incorrect = PseudoClass.getPseudoClass("incorrect");

    public void setText(String text) {
        ui.setText(text);
    }

    public StringLabel(EZElementContainer element,String key, JSONObject object) {
        this.parent = element;
        id = key;
        this.object = object;

        String description = object.get("description").toString();
        ui.setTooltip(new Tooltip(description));

        String defaultValue = object.getOrDefault("default", "minecraft:datapack").toString();
        ui.setText(defaultValue);

        ui.getStylesheets().add("/src/main/resources/css/ErrorableTextField.css");
        ui.getStyleClass().add("text");
        ui.getStyleClass().add("font");

        boolean allowSpacing;
        allowSpacing = (boolean) object.getOrDefault("allowSpacing", true);
        if(allowSpacing == false) {
            ui.getStyleClass().add("color");
            ui.textProperty().addListener((observable, oldValue, newValue) -> {
                ui.pseudoClassStateChanged(incorrect, newValue.isBlank() || newValue.contains(" "));
                resize();
            });
        }
        else {
            ui.textProperty().addListener(((observable, oldValue, newValue) -> {
                ui.pseudoClassStateChanged(incorrect, newValue.isBlank());
                resize();
            }));
        }

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

            parent.resize();
        });

        // https://stackoverflow.com/questions/12737829/javafx-textfield-resize-to-text-length
    }

    private JSONObject object;

    @Override
    public StringLabel clone(EZBlock block) {
        StringLabel label = new StringLabel(parent,id,object);
        label.parent = block;
        label.setText(ui.getText());
        return label;
    }
}
