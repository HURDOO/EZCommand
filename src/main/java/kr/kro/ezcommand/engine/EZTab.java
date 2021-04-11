package kr.kro.ezcommand.engine;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.engine.parser.EZBlock;

import java.util.ArrayList;
import java.util.List;

public class EZTab
{
    public static EZTab nowTab = null;

    public EZTab() {
        ui = new Tab();
        ui.setContent(new AnchorPane());
    }

    /**
     * Tab에 포함되어 있는 그롭들.
     */
    private List<EZGroup> groups = new ArrayList<>();
    public List<EZGroup> getGroups() {
        return groups;
    }

    public addBlock(EZBlock block,EZBlock parent) {
        EZGroup group = findGroupOfBlock(block);
        group.addBlockBelow(parent,block);
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
        for(EZGroup group : groups) {
            command += group.toCommand() + "\n";
        }
        return command;
    }

    /**
     * 주어진 블록이 속하여 있는 그룹을 반환합니다.
     * 그룹을 찾지 못한 경우 null을 반환합니다.
     * @param block
     * @return ezGroup
     */
    public EZGroup findGroupOfBlock(EZBlock block) {
        for(EZGroup group : groups) {
            if(group.getBlocks().contains(block))
                return group;
        }
        return null;
    }
}

