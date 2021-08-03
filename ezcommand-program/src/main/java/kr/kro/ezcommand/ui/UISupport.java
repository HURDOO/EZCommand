package kr.kro.ezcommand.ui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import kr.kro.ezcommand.engine.EZTab;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.ui.stage.MainStage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class UISupport extends Node {

    private boolean isExampleBlock = true;
    public boolean isExampleBlock() {
        return isExampleBlock;
    }
    public void setExampleBlock(boolean value) {
        isExampleBlock = value;
    }

    //private List<UISupport> elementStack;
    public UISupport() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() != MouseButton.PRIMARY) return;
                UISupport ui = UISupport.this;

                if(isExampleBlock)
                {
                    isExampleBlock = false;
                    UISupport clone = ui.clone();
                    List<Node> blockList = BlockList.getUi().getChildren();
                    int index = blockList.indexOf(ui);
                    blockList.remove(index);
                    blockList.add(index,clone);
                }
                /*else
                {
                    MainStage.getBlockWorkspace().getChildren().removeAll(elementStack);
                }
                for(UISupport u : elementStack)
                {
                    MainStage.backPane.getChildren().add(u);
                    u.setLayoutX(u.getLocalToSceneTransform().getTx());
                    u.setLayoutY(u.getLocalToSceneTransform().getTy());
                }*/

                MainStage.backPane.getChildren().add(ui);
                setLayoutX(getLocalToSceneTransform().getTx());
                setLayoutY(getLocalToSceneTransform().getTy());

                event.consume();
                ui.setMouseTransparent(true);
                event.setDragDetect(true);

                saveCurrentMouse();
                ui.requestFocus();
            }
        });

        setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    UISupport.this.startFullDrag();
                }
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() != MouseButton.PRIMARY) return;

                Point mouse = MouseInfo.getPointerInfo().getLocation();
                setLayoutX(mouse.getX() - window_x - diff_x);
                setLayoutY(mouse.getY() - window_y - diff_y);

                event.setDragDetect(false); // what about deleting this?
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() != MouseButton.PRIMARY) return;

                UISupport ui = UISupport.this;
                setMouseTransparent(false);

                Bounds uiBounds = localToScene(getLayoutBounds());
                Bounds paneBounds = EZTab.nowTab.getUiPane().localToScene(EZTab.nowTab.getUiPane().getLayoutBounds());
                Bounds paneScreenBounds = EZTab.nowTab.getUiPane().localToScreen(EZTab.nowTab.getUiPane().getLayoutBounds());
                Point mouse = MouseInfo.getPointerInfo().getLocation();
                MainStage.backPane.getChildren().remove(ui);

                if(mouse.getX() >= paneScreenBounds.getMinX()) {
                    ui.setLayoutX(uiBounds.getMinX() - paneBounds.getMinX());
                    ui.setLayoutY(uiBounds.getMinY() - paneBounds.getMinY());

                    EZTab.nowTab.getUiPane().getChildren().add(ui);
                }
            }
        });
    }

    private static double window_x,window_y,diff_x,diff_y;
    private void saveCurrentMouse() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Bounds bounds = localToScreen(getBoundsInLocal());
        window_x = bounds.getMinX() - getLayoutX();
        window_y = bounds.getMinY() - getLayoutY();
        diff_x = mouse.getX() - bounds.getMinX();
        diff_y = mouse.getY() - bounds.getMinY();
    }

    public abstract UISupport clone();
    public abstract double getHeight();
}
