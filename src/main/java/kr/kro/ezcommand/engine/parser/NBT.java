package kr.kro.ezcommand.engine.parser;

import javafx.scene.Node;

public class NBT implements EZElementContainer {
    private EZBlockElement value;
    public void setValue(EZBlockElement element) {
        value = element;
    }
    private final String name;
    private final String parse;

    public NBT(String name,String parse) {
        this.name = name;
        this.parse = parse;
    }

    public Node getUi() {
        return value.getUI();
    }
    public String toCommand() {
        return parse + ":" + value.toCommand();
    }

    @Override
    public void resize() {

    }
}
