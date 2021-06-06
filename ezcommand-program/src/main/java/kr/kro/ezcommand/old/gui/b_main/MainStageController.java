package kr.kro.ezcommand.old.gui.b_main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.inner.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainStageController implements Initializable {

    @FXML MenuItem newTab;
    @FXML
    public TabPane Tabs;
    @FXML
    public AnchorPane blockListPane;
    @FXML
    public SplitPane divider;
    @FXML MenuItem convertToCommand;
    @FXML MenuItem convertToDatapack;
    @FXML Menu editMenu;
    @FXML MenuItem move;
    @FXML MenuItem delete;

    public EZTab nowEZTab = null;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        newTab.setOnAction(event -> { // Menu -> File -> New File
            try {
                StageManager.createNewFileStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Tabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                nowEZTab = EZProject.getEZTab.get(Tabs.getSelectionModel().getSelectedItem());
                hideDefaultButtons();
                //System.out.println("Tab Selection changed");
            }
        });
        convertToCommand.setOnAction(event -> {
            for(int i=0;i<EZProject.ezTabs.size();i++)
            {
                EZTab tab = EZProject.ezTabs.get(i);
                tab.convertToCommand();
            }
        });
        convertToDatapack.setOnAction(event -> {
            try {
                StageManager.createDatapackConvertionStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        editMenu.setOnShowing(event -> {
            if(BlockListGUIController.selectedBlock == null || BlockListGUIController.selectedBlock.blockType == EZBlockType.onFunction)
            {
                move.setDisable(true);
                delete.setDisable(true);
            }
            else
            {
                move.setDisable(false);
                delete.setDisable(false);
            }
        });
        delete.setOnAction(event -> {
            int index = BlockListGUIController.selectedBlock.parent.Blocks.indexOf(BlockListGUIController.selectedBlock);
            BlockListGUIController.selectedBlock.parent.Blocks.remove(BlockListGUIController.selectedBlock.parent.Blocks.indexOf(BlockListGUIController.selectedBlock));
            StageManager.cont.nowEZTab.blockPane.getChildren().remove(BlockListGUIController.selectedBlock.pane);
            BlockListGUIController.selectedBlock.parent.reassemble();

            StageManager.cont.hideDefaultButtons();
            BlockListGUIController.removeSelectedBlock();
        });
    }

    public static void mouseFollowerFrom(EZBlock block) {
        if (block.blockType == EZBlockType.onFunction) {
            block.pane.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    event.consume();

                    block.pane.setMouseTransparent(true);
                    //System.out.println("Event on Source: mouse pressed");
                    event.setDragDetect(true);
                    //mouse = MouseInfo.getPointerInfo().getLocation();

                    hideExecutionButtons();
                    BlockListGUIController.setSelectedBlock(block);

                    StageManager.cont.nowEZTab.hideArrow();
                }
            });

            block.pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    block.pane.setMouseTransparent(false);
                    //System.out.println("Event on Source: mouse released");
                    //mouse = null;


                    EZMcfunction function = (EZMcfunction) block;
                    function.reassemble();
                    for (int i = 0; i < function.Blocks.size(); i++) {
                        function.Blocks.get(i).pane.setVisible(true);
                    }
                }
            });

            block.pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Point nowPoint = MouseInfo.getPointerInfo().getLocation();
                    Bounds nodeBound = block.pane.localToScreen(block.pane.getBoundsInLocal());
                    double diff_x = nodeBound.getMinX() - block.pane.getLayoutX();
                    double diff_y = nodeBound.getMinY() - block.pane.getLayoutY();
                    //double loc_x = mouse.getX() - nodeBound.getMinX();
                    //double loc_y = mouse.getY() - nodeBound.getMinY();
                    //System.out.println("Event on Source: mouse dragged");
                    block.pane.setLayoutX(nowPoint.getX() - diff_x);
                    block.pane.setLayoutY(nowPoint.getY() - diff_y);
                    event.setDragDetect(false);
                }
            });

            block.pane.setOnDragDetected(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    block.pane.startFullDrag();
                    //System.out.println("Event on source: mouse drag detected");

                    EZMcfunction function = (EZMcfunction) block;
                    for (int i = 0; i < function.Blocks.size(); i++) {
                        function.Blocks.get(i).pane.setVisible(false);
                    }
                }
            });


        } else {
            block.pane.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    event.consume();

                    hideExecutionButtons();
                    BlockListGUIController.setSelectedBlock(block);
                }
            });

            /*block.pane.setOnMouseReleased(new EventHandler <MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    block.pane.setMouseTransparent(false);
                    //System.out.println("Event on Source: mouse released");
                    //mouse = null;
                }
            });

            block.pane.setOnMouseDragged(new EventHandler <MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    Point nowPoint = MouseInfo.getPointerInfo().getLocation();
                    Bounds nodeBound = block.pane.localToScreen(block.pane.getBoundsInLocal());
                    double diff_x = nodeBound.getMinX()-block.pane.getLayoutX();
                    double diff_y = nodeBound.getMinY()-block.pane.getLayoutY();
                    //double loc_x = mouse.getX() - nodeBound.getMinX();
                    //double loc_y = mouse.getY() - nodeBound.getMinY();
                    //System.out.println("Event on Source: mouse dragged");
                    block.pane.setLayoutX(nowPoint.getX() - diff_x);
                    block.pane.setLayoutY(nowPoint.getY() - diff_y);
                    event.setDragDetect(false);
                }
            });

            block.pane.setOnDragDetected(new EventHandler <MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    block.pane.startFullDrag();
                    //System.out.println("Event on source: mouse drag detected");
                }
            });*/
        }
    }

    //public static Point mouse = null;

    public void mouseFollowerTo(Node node) {

    }

    /*public static void showBlockList(Node node, List<Button> list)
    {
        node.setOnMousePressed(event1 -> {
            for(int i=0;i<list.size();i++)
            {
                list.get(i).setDisable(false);
            }
        });
    }*/
    public static void hideDefaultButtons() {
        for (int i = 0; i < BlockListGUIController.defaultButton.size(); i++) {
            BlockListGUIController.defaultButton.get(i).setDisable(true);
        }

        for (int i = 0; i < BlockListGUIController.executionButton.size(); i++) {
            BlockListGUIController.executionButton.get(i).setDisable(false);
        }
    }

    public static void hideExecutionButtons() {
        for (int i = 0; i < BlockListGUIController.defaultButton.size(); i++) {
            BlockListGUIController.defaultButton.get(i).setDisable(false);
        }

        for (int i = 0; i < BlockListGUIController.executionButton.size(); i++) {
            BlockListGUIController.executionButton.get(i).setDisable(true);
        }
    }
}



/*
        tab.setContent(pane);

        GridPane pane = new GridPane();
        TextField sourceFld = new TextField("Source");
        TextField targetFld = new TextField("Target");

        // Set the Size of the TextFields
        sourceFld.setPrefSize(200, 20);
        targetFld.setPrefSize(200, 20);

        // Create the Labels
        Label sourceLbl = new Label("Source Node:");
        Label targetLbl = new Label("Target Node:");

        // Create the GridPane
        pane.setHgap(5);
        pane.setVgap(20);

        // Add the Labels and Fields to the Pane
        pane.addRow(0, sourceLbl, sourceFld);
        pane.addRow(1, targetLbl, targetFld);

        // Add mouse event handlers for the source
        sourceFld.setOnMousePressed(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                sourceFld.setMouseTransparent(true);
                System.out.println("Event on Source: mouse pressed");
                event.setDragDetect(true);
            }
        });

        sourceFld.setOnMouseReleased(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                sourceFld.setMouseTransparent(false);
                System.out.println("Event on Source: mouse released");
            }
        });

        sourceFld.setOnMouseDragged(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                System.out.println("Event on Source: mouse dragged");
                event.setDragDetect(false);
            }
        });

        sourceFld.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                sourceFld.startFullDrag();
                System.out.println("Event on Target: mouse drag detected");
            }
        });

        // Add mouse event handlers for the target
        targetFld.setOnMouseDragEntered(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse dragged");
            }
        });

        targetFld.setOnMouseDragOver(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse drag over");
            }
        });

        targetFld.setOnMouseDragReleased(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                targetFld.setText(sourceFld.getSelectedText());
                System.out.println("Event on Target: mouse drag released");
            }
        });

        targetFld.setOnMouseDragExited(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse drag exited");
            }
        });*/

