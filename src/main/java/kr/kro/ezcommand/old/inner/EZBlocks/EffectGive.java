package kr.kro.ezcommand.old.inner.EZBlocks;

import javafx.fxml.FXMLLoader;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.inner.EZBlock;
import kr.kro.ezcommand.old.inner.EZBlockType;
import kr.kro.ezcommand.old.inner.Types.Effect;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.io.IOException;

public class EffectGive extends EZBlock
{
    public Selector.selectorList target;
    public String targetPlayerName;
    public Effect.EffectType effectType;
    public int seconds;
    public int amount;
    public boolean hideParticle;

    public EffectGive()
    {
        BlockListGUIController.nowEffectGive = this;
        blockType = EZBlockType.EffectGive;

        if(BlockListGUIController.defaultEffectGive == null)
        {
            target = Selector.selectorList.executor;
            targetPlayerName = null;
            effectType = Effect.EffectType.SPEED;
            seconds = 60;
            amount = 1;
            hideParticle  = true;
            parent = null;
        }
        else
        {
            target = BlockListGUIController.defaultEffectGive.target;
            targetPlayerName = BlockListGUIController.defaultEffectGive.targetPlayerName;
            effectType = BlockListGUIController.defaultEffectGive.effectType;
            seconds = BlockListGUIController.defaultEffectGive.seconds;
            amount = BlockListGUIController.defaultEffectGive.amount;
            hideParticle = BlockListGUIController.defaultEffectGive.hideParticle;
            parent = BlockListGUIController.selectedBlock.parent;
        }

        try {
            pane = FXMLLoader.load(EffectGive.class.getResource("/kr/kro/ezcommand/gui/b_main/blocks/effect/EffectGive.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
