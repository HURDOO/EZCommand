package kr.kro.ezcommand.old.inner.EZBlocks;

import javafx.fxml.FXMLLoader;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.gui.b_main.blocks.Teleport.TeleportToEntityController;
import kr.kro.ezcommand.old.inner.EZBlock;
import kr.kro.ezcommand.old.inner.EZBlockType;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.io.IOException;

public class TeleportToEntity extends EZBlock
{
    public Selector.selectorList target;
    public String targetPlayerName;

    public Selector.selectorList destination;
    public String destinationPlayerName;

    public TeleportToEntity()
    {
        BlockListGUIController.nowTpEntity = this;

        blockType = EZBlockType.TeleportToEntity;

        if(BlockListGUIController.defaultTpEntity == null)
        {
            target = Selector.selectorList.executor;
            targetPlayerName = "";
            destination = Selector.selectorList.executor;
            destinationPlayerName = "";
            parent = null;
        }
        else
        {
            target = BlockListGUIController.defaultTpEntity.target;
            targetPlayerName = BlockListGUIController.defaultTpEntity.targetPlayerName;

            destination = BlockListGUIController.defaultTpEntity.destination;
            destinationPlayerName = BlockListGUIController.defaultTpEntity.destinationPlayerName;
            parent = BlockListGUIController.selectedBlock.parent;
        }
        try {
            pane=FXMLLoader.load(TeleportToEntityController.class.getResource("/kr/kro/ezcommand/gui/b_main/blocks/Teleport/TeleportToEntity.fxml"));
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

        String destination1;
        if(destination == Selector.selectorList.playerNickname)
            destination1 = destinationPlayerName;
        else if(destination == Selector.selectorList.executor)
            destination1 = target.toStringCode();
        else
            destination1 = destination.toStringCode() + "[limit=1]";

        return "tp " + target1 + " " + destination1;
    }
}
