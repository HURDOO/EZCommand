package kr.kro.hurdoo.je1165.type;

import javafx.scene.Node;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;
import kr.kro.ezcommand.engine.parser.file.EZData;

public class NBT implements EZData, EZElementContainer {
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
