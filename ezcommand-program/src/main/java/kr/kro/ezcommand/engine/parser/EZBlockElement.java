package kr.kro.ezcommand.engine.parser;

import javafx.scene.Node;
import kr.kro.ezcommand.engine.parser.file.EZData;

public interface EZBlockElement extends EZData {
    Node getUI();

    String getId();

    String toCommand();

    //EZElementContainer getParent();

    EZBlockElement clone() throws CloneNotSupportedException;
}
