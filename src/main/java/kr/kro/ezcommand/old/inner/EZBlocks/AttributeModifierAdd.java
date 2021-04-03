package kr.kro.ezcommand.old.inner.EZBlocks;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.gui.b_main.blocks.Attribute.AttributeModifierAddController;
import kr.kro.ezcommand.old.inner.Types.Attribute;
import kr.kro.ezcommand.old.inner.EZBlock;
import kr.kro.ezcommand.old.inner.EZBlockType;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.io.IOException;

public class AttributeModifierAdd extends EZBlock
{
    public Selector.selectorList target;
    public String targetPlayerName;

    public Attribute.AttributeList attribute;
    public String name;
    public String uuid;
    public double amount;
    public Attribute.modifierType modifierType;

    public AttributeModifierAddController thisController;

    public AttributeModifierAdd()
    {
        blockType = EZBlockType.AttributeModifierAdd;
        BlockListGUIController.nowAttributeModifierAdd = this;

        if(BlockListGUIController.defaultAttributeModifierAdd == null)
        {
            target = Selector.selectorList.executor;
            targetPlayerName = "";
            attribute = Attribute.AttributeList.Generic_ATTACK_DAMAGE;
            name = "Attack_plus";
            uuid = "00000001-0001-0001-0001-000000000001";
            amount = 1.0;
            modifierType = Attribute.modifierType.ADD;
            parent = null;
        }
        else
        {
            target = BlockListGUIController.defaultAttributeModifierAdd.target;
            targetPlayerName = BlockListGUIController.defaultAttributeModifierAdd.targetPlayerName;
            attribute = BlockListGUIController.defaultAttributeModifierAdd.attribute;
            name = BlockListGUIController.defaultAttributeModifierAdd.name;
            uuid = BlockListGUIController.defaultAttributeModifierAdd.uuid;
            amount = BlockListGUIController.defaultAttributeModifierAdd.amount;
            modifierType = BlockListGUIController.defaultAttributeModifierAdd.modifierType;
            parent = BlockListGUIController.selectedBlock.parent;
        }

        FXMLLoader root = null;
        root = new FXMLLoader(AttributeModifierAdd.class.getResource("/kr/kro/ezcommand/gui/b_main/blocks/Attribute/AttributeModifierAdd.fxml"));
        try {
            pane = (AnchorPane) root.load();
            thisController = root.getController();
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
        return "attribute " + target1 + " " + attribute.toStringCode() + " modifier add "
                + uuid + " " + name + " " + amount + " " + modifierType.toStringCode();
    }
}
