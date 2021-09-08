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
    private final List<EZBlock> blocks = new ArrayList<>();
    public List<EZBlock> getBlocks() {
        return blocks;
    }

    /**
     * 제공된 모든 블록을 포함시킵니다.
     * @param severalBlocks 이 탭에 포함시킬 블록들
     */
    public void addBlock(EZBlock... severalBlocks) {
        for(EZBlock block : severalBlocks) {
            addBlockAt(blocks.size(), block);
        }
    }

    /**
     * 제공된 부모 블록 하위로, 이어서 제공되는 모든 블록을 순서대로 포함시킵니다.
     * @param parent 부모
     * @param severalBlocks 자식들
     */
    @SuppressWarnings("unused")
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
     * @param children 자식
     * @param severalBlocks 부모들
     */
    @SuppressWarnings("unused")
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
     * @param index 순서
     * @param block 블록
     */
    public void addBlockAt(int index,EZBlock block) {
        blocks.add(index,block);
    }

    public void changeBlock(EZBlock oldValue,EZBlock newValue) {
        blocks.set(blocks.indexOf(oldValue),newValue);
    }

    /**
     * 제공된 모든 블록을 삭제합니다.
     * @param severalBlocks 삭제할 블록들
     */
    public void removeBlock(EZBlock... severalBlocks) {
        for(EZBlock block : severalBlocks) {
            blocks.remove(block);
        }
    }

    /**
     * 제공된 블록을 포함하여, 하위의 모든 블록을 삭제합니다.
     * @param block 삭제할 블록들의 최상단(부모) 블록
     */
    @SuppressWarnings("unused")
    public void removeBlockFrom(EZBlock block) {
        int index = blocks.indexOf(block);
        while(blocks.size() > index) {
            blocks.remove(index);
        }
    }

    /**
     * 제공된 블록을 포함하지 않고, 하위의 모든 블록을 삭제합니다.
     * @param block 삭제할 블록들의 한칸 위 블록
     */
    @SuppressWarnings("unused")
    public void removeBlockBelow(EZBlock block) {
        int index = blocks.indexOf(block) + 1;
        while(blocks.size() >= index) {
            blocks.remove(index);
        }
    }

    /**
     * 제공된 블럭을 포함하여, 상위의 모든 블록을 삭제합니다.
     * @param block 삭제할 블록들의 최하단(자식) 블록
     */
    @SuppressWarnings("unused")
    public void removeBlockUntil(EZBlock block) {
        int index = blocks.indexOf(block);
        if (index > 0) {
            blocks.subList(0, index).clear();
        }
    }

    /**
     * 제공된 블럭을 포함하지 않고, 상위의 모든 블록을 삭제합니다.
     * @param block 삭제할 블록들의 한칸 아래 블록
     */
    @SuppressWarnings("unused")
    public void removeBlockUpon(EZBlock block) {
        int index = blocks.indexOf(block) -1;
        if (index > 0) {
            blocks.subList(0, index).clear();
        }
    }

    /**
     * 제공된 순서에 있는 블록을 삭제합니다.
     * @param index 순서
     */
    @SuppressWarnings("unused")
    public void removeBlockAt(int index) {
        blocks.remove(index);
    }

    private final Tab ui;
    public Tab getUi() {
        return ui;
    }
    public AnchorPane getUiPane() {
        return (AnchorPane) ui.getContent();
    }

    public String toCommand() {
        StringBuilder command = new StringBuilder();
        for(EZBlock block : blocks) {
            command.append(getAllChildrenCommands(block)).append("\n");
        }
        return command.toString();
    }

    private String getAllChildrenCommands(EZBlock block) {
        if(block.getChildren() == null) return block.toCommand();
        return block.toCommand() + "\n" + getAllChildrenCommands(block.getChildren());
    }


}

