package kr.kro.ezcommand.engine;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.engine.parser.EZBlock;

import java.util.ArrayList;
import java.util.Arrays;
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
    private List<EZBlock> blocks = new ArrayList<>();
    public List<EZBlock> getBlocks() {
        return blocks;
    }

    /**
     * 제공된 모든 블록을 포함시킵니다.
     * @param severalBlocks
     */
    public void addBlock(EZBlock... severalBlocks) {
        for(EZBlock block : severalBlocks) {
            addBlockAt(blocks.size(), block);
        }
    }

    /**
     * 제공된 부모 블록 하위로, 이어서 제공되는 모든 블록을 순서대로 포함시킵니다.
     * @param parent
     * @param severalBlocks
     */
    public void addBlockBelow(EZBlock parent, EZBlock... severalBlocks) {
        int index = blocks.indexOf(parent) + 1;
        if(index == 0) throw new IllegalArgumentException("Unknown parent");
        /*for(int i=0;i<severalBlocks.length;i++) {
            addBlockAt(index+i,severalBlocks[i]);
            index++;
        }*/
        blocks.addAll(index, Arrays.asList(severalBlocks));
    }

    /**
     * 제공된 자식 블록 상위로, 이어서 제공되는 모든 블록을 순서대로 포함시킵니다.
     * @param children
     * @param severalBlocks
     */
    public void addBlockUpon(EZBlock children, EZBlock... severalBlocks) {
        int index = blocks.indexOf(children);
        if(index == -1) throw new IllegalArgumentException("Unknown children");
        /*for(int i=0;i< severalBlocks.length;i++) {
            addBlockAt(index+i,severalBlocks[i]);
            index++;
        }*/
        blocks.addAll(index,Arrays.asList(severalBlocks));
    }

    /**
     * 제공된 순서에 해당 블록을 포함시킵니다.
     * 참고로, 원래 해당 위치에 있던 블록은 한 칸 뒤로 밀려납니다.
     * @param index
     * @param block
     */
    public void addBlockAt(int index,EZBlock block) {
        blocks.add(index,block);
    }

    /**
     * 제공된 모든 블록을 삭제합니다.
     * @param severalBlocks
     */
    public void removeBlock(EZBlock... severalBlocks) {
        for(EZBlock block : severalBlocks) {
            blocks.remove(block);
        }
    }

    /**
     * 제공된 블록을 포함하여, 하위의 모든 블록을 삭제합니다.
     * @param block
     */
    public void removeBlockFrom(EZBlock block) {
        int index = blocks.indexOf(block);
        while(blocks.size() > index) {
            blocks.remove(index);
        }
    }

    /**
     * 제공된 블록을 포함하지 않고, 하위의 모든 블록을 삭제합니다.
     * @param block
     */
    public void removeBlockBelow(EZBlock block) {
        int index = blocks.indexOf(block) + 1;
        while(blocks.size() >= index) {
            blocks.remove(index);
        }
    }

    /**
     * 제공된 블럭을 포함하여, 상위의 모든 블록을 삭제합니다.
     * @param block
     */
    public void removeBlockUntil(EZBlock block) {
        int index = blocks.indexOf(block);
        for(int i=0;i<index;i++) {
            blocks.remove(0);
        }
    }

    /**
     * 제공된 블럭을 포함하지 않고, 상위의 모든 블록을 삭제합니다.
     * @param block
     */
    public void removeBlockUpon(EZBlock block) {
        int index = blocks.indexOf(block) -1;
        for(int i=0;i<index;i++) {
            blocks.remove(0);
        }
    }

    /**
     * 제공된 순서에 있는 블록을 삭제합니다.
     * @param index
     */
    public void removeBlockAt(int index) {
        blocks.remove(index);
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
            command += block.toCommand() + "\n";
        }
        return command;
    }


}

