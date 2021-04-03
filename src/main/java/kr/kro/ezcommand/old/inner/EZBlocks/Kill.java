package kr.kro.ezcommand.old.inner.EZBlocks;

import javafx.fxml.FXMLLoader;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.inner.EZBlock;
import kr.kro.ezcommand.old.inner.EZBlockType;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.io.IOException;

public class Kill extends EZBlock
{
    public Selector.selectorList target;
    public String targetPlayerName;

    public Kill()
    {
        BlockListGUIController.nowKill = this;
        blockType = EZBlockType.KILL;

        if(BlockListGUIController.defaultKill == null)
        {
            target = Selector.selectorList.executor;
            targetPlayerName = "";
            parent = null;
        }
        else
        {
            target = BlockListGUIController.defaultKill.target;
            targetPlayerName = BlockListGUIController.defaultKill.targetPlayerName;
            parent = BlockListGUIController.selectedBlock.parent;
        }

        try {
            pane = FXMLLoader.load(Kill.class.getResource("/kr/kro/ezcommand/gui/b_main/blocks/kill/Kill.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString() {
        String target1;
        if(target == Selector.selectorList.playerNickname)
            target1 = targetPlayerName;
        else
            target1 = target.toStringCode();

        return "kill " + target1;
    }
}
