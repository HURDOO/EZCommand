package kr.kro.hurdoo.je1165.type;

import javafx.scene.Node;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;
import kr.kro.ezcommand.engine.parser.file.EZData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NBT implements EZData, EZElementContainer {

    public static final List<NBT> nbts = new ArrayList<>();
    public static final HashMap<String,NBT> nbtMap = new HashMap<>();

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
    public String getName() {
        return name;
    }

    @Override
    public void resize() {

    }

}
