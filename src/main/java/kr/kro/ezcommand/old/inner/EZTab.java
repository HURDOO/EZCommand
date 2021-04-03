package kr.kro.ezcommand.old.inner;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.kro.ezcommand.old.gui.b_main.MainStageController.hideDefaultButtons;

public class EZTab {

    public Tab tab = new Tab();

    public AnchorPane blockPane = new AnchorPane(); //블록창
    public AnchorPane codePane = new AnchorPane(); //코드창

    public TextArea codeText = new TextArea();

    public EZTab(EZTabType type,String name)
    {

        this.name=name;
        this.type=type;

        EZProject.ezTabs.add(this);
        EZProject.getEZTab.put(tab,this);

        if(type.equals(EZTabType.MCFUNCTION))
        {
            tab.setText(name);
            ScrollPane pane = new ScrollPane();
            pane.setPrefSize(2000,1000);
            pane.setFitToHeight(true);
            pane.setFitToWidth(true);
            pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tab.setContent(pane);
        }
        StageManager.cont.Tabs.getTabs().add(tab);
        StageManager.cont.Tabs.getSelectionModel().select(tab);

        SplitPane splitPane= new SplitPane(); //블록창과 코드창 구분선
        tab.setContent(splitPane);
        splitPane.autosize();
        splitPane.setOrientation(Orientation.VERTICAL);

        splitPane.getItems().add(blockPane);
        splitPane.getItems().add(codePane);

        blockPane.autosize();
        codePane.autosize();

        StageManager.cont.blockListPane.getChildren().clear();
        StageManager.cont.blockListPane.getChildren().add(EZProject.BlockListGUI);

        blockPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideDefaultButtons();
                BlockListGUIController.removeSelectedBlock();
            }
        });
        hideDefaultButtons();

        /* ARROW */

        Button up = new Button();
        Button down = new Button();

        up.setText("▲");
        down.setText("▼");

        up.setOnMouseClicked(event1 -> {
            EZBlock block = BlockListGUIController.selectedBlock;
            int nowindex = block.parent.Blocks.indexOf(block);

            if(nowindex != 0)
            {
                EZBlock frontblock = block.parent.Blocks.get(nowindex-1);
                block.parent.Blocks.set(nowindex-1,BlockListGUIController.selectedBlock);
                block.parent.Blocks.set(nowindex,frontblock);
                block.parent.reassemble();
                showArrow(block);
            }
        });

        down.setOnMouseClicked(event1 -> {
            EZBlock block = BlockListGUIController.selectedBlock;
            int nowindex = block.parent.Blocks.indexOf(block);

            if(nowindex != block.parent.Blocks.size()-1)
            {
                EZBlock frontblock = block.parent.Blocks.get(nowindex+1);
                block.parent.Blocks.set(nowindex+1,block);
                block.parent.Blocks.set(nowindex,frontblock);
                block.parent.reassemble();
                showArrow(block);
            }
        });

        arrow.getChildren().add(up);
        arrow.getChildren().add(down);

        arrow.setVisible(false);
        blockPane.getChildren().add(arrow);

        /* ARROW */

        codePane.getChildren().add(codeText);
        codeText.setEditable(false);
    }


    public String name;
    public EZTabType type;

    public List<EZMcfunction> Executions = new ArrayList<>();
    public HashMap<AnchorPane,EZMcfunction> getExecutions = new HashMap<>();

    /* ARROW */

    VBox arrow = new VBox();

    public void showArrow(EZBlock block) {
        arrow.setVisible(true);
        arrow.setLayoutX(block.pane.getBoundsInParent().getMaxX());
        arrow.setLayoutY(block.pane.getBoundsInParent().getMinY());
    }

    public void hideArrow()
    {
        arrow.setVisible(false);
    }

    /* ARROW */

    public void convertToCommand()
    {
        codeText.clear();
        for(int i=0;i<Executions.size();i++)
        {
            EZMcfunction function = Executions.get(i);
            codeText.appendText(this.name + ":" + function.name+" (.mcfunction)\n\n");
            for(int j=0;j<function.Blocks.size();j++)
            {
                codeText.appendText("/" + function.Blocks.get(j).toString() + "\n");
            }
            codeText.appendText("\n\n");
        }
    }
}
