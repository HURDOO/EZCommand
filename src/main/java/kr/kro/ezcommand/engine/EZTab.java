package kr.kro.ezcommand.engine;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.engine.parser.EZBlock;

import java.util.ArrayList;
import java.util.List;

public class EZTab
{

    public EZTab() {
        ui = new Tab();
        ui.setContent(new AnchorPane());
    }

    /**
     * Tab에 포함되어 있는 블록들. (블록이라기엔 대부분이 함수 단위일테지만..)
     */
    private List<EZBlock> blocks = new ArrayList<>();
    public List<EZBlock> getBlocks() {
        return blocks;
    }
    public void addBlock(EZBlock block) {
        blocks.add(block);
        getUiPane().getChildren().add(block.getUi());
    }

    private Tab ui;
    public Tab getUi() {
        return ui;
    }
    public AnchorPane getUiPane() {
        return (AnchorPane) ui.getContent();
    }

    public String toCommand() {
        String command = "";
        for(EZBlock block : blocks) {
            command += block.toCommand();
        }
        return command;
    }
}
