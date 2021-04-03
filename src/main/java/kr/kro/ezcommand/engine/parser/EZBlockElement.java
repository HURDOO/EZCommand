package kr.kro.ezcommand.engine.parser;

import javafx.scene.Node;

public interface EZBlockElement
{
    Node getUI();

    String getId();

    String toCommand();
}
