package kr.kro.ezcommand.old.inner;

import javafx.fxml.FXMLLoader;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EZMcfunction extends EZBlock
{
    public EZMcfunctionType type;
    public String name;

    public EZMcfunction(EZMcfunctionType type)
    {
        blockType = EZBlockType.onFunction;
        BlockListGUIController.nowFunction = this;
        this.type = type;

        if(BlockListGUIController.defaultFunction==null)
            name = "myfunction";
        else
            name = BlockListGUIController.defaultFunction.name;

        try {
            pane = FXMLLoader.load(EZMcfunction.class.getResource("/kr/kro/ezcommand/gui/b_main/blocks/onFunction/onFunction.fxml"));
        } catch (IOException e) {

            e.printStackTrace();
        }

        if(StageManager.cont.nowEZTab!=null) {
            StageManager.cont.nowEZTab.Executions.add(this);
            StageManager.cont.nowEZTab.getExecutions.put(pane, this);
        }

        /*
        BlockListGUIController.nowFunction = function1;
        function1.create(false);
        BlockListGUIController.cont.nowEZTab.blockPane.getChildren().add(function1.getPane());
        MainStageController.mouseFollowerFrom(function1.getPane());
        //MainStageController.showBlockList(function1.getPane(),defaultButton);

         */
        parent = this;

        upButton = null;
        downButton = null;
    }

    public List<EZBlock> Blocks = new ArrayList<>();
    public void reassemble()
    {
        if(Blocks.size() == 0) return;
        Blocks.get(0).pane.setLayoutX(this.pane.getBoundsInParent().getMinX());
        Blocks.get(0).pane.setLayoutY(this.pane.getBoundsInParent().getMaxY());
        if(Blocks.size() == 1) return;

        for(int i=1;i<Blocks.size();i++)
        {
            Blocks.get(i).pane.setLayoutX(Blocks.get(i-1).pane.getBoundsInParent().getMinX());
            Blocks.get(i).pane.setLayoutY(Blocks.get(i-1).pane.getBoundsInParent().getMaxY());
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
