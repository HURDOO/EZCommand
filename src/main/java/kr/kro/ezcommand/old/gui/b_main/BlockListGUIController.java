package kr.kro.ezcommand.old.gui.b_main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.gui.*;
import kr.kro.ezcommand.old.inner.*;
import kr.kro.ezcommand.old.inner.EZBlocks.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BlockListGUIController implements Initializable
{

    @FXML VBox execution;
    @FXML VBox world;
    @FXML VBox entity;

    public static List<Button> defaultButton = new ArrayList<>();
    public static List<Button> executionButton = new ArrayList<>();
    public static EZBlock selectedBlock = null;

    public static void setSelectedBlock(EZBlock block)
    {
        selectedBlock = block;
        if(block.blockType == EZBlockType.onFunction)
        {
            StageManager.cont.nowEZTab.hideArrow();
        }
        else
        {
            StageManager.cont.nowEZTab.showArrow(block);
        }
    }
    public static void removeSelectedBlock()
    {
        selectedBlock = null;
        StageManager.cont.nowEZTab.hideArrow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            buildWEATHER();
            //execution.setLayoutY(execution.getChildren().get(execution.getChildren().size()-1).getLayoutBounds().getMaxY()-execution.getLayoutY());
            buildFunction();
            buildTeleportToEntity();
            buildKill();
            if(EZProject.Edition == EZProject.MCEdition.JAVA)
                buildAttributeModifierAdd();
            //buildEffectGive();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static WEATHER defaultWeather = null;
    public static WEATHER nowWeather = null;

    private void buildWEATHER()
    {

        HBox box = new HBox();
        world.getChildren().add(box);
        Button button = new Button();
        button.setText("+");
        box.getChildren().add(button);
        defaultButton.add(button);

        WEATHER weather = new WEATHER();
        nowWeather = weather;
        defaultWeather = weather;

        box.getChildren().add(weather.pane);
        weather.pane.setLayoutX(button.getBoundsInParent().getMaxX());

        button.setOnMouseClicked(event -> {
            WEATHER weather1 = new WEATHER();
            StageManager.cont.nowEZTab.blockPane.getChildren().add(weather1.pane);
            MainStageController.mouseFollowerFrom(weather1);
            if(selectedBlock.blockType == EZBlockType.onFunction)
                selectedBlock.parent.Blocks.add(0,weather1);
            else
                selectedBlock.parent.Blocks.add(selectedBlock.parent.Blocks.indexOf(selectedBlock)+1,weather1);

            weather1.parent.reassemble();
            setSelectedBlock(weather1);
            StageManager.cont.nowEZTab.showArrow(selectedBlock);
        });
    }

    public static EZMcfunction defaultFunction = null;
    public static EZMcfunction nowFunction = null;

    private void buildFunction()
    {

        HBox box = new HBox();
        execution.getChildren().add(box);
        Button button = new Button();
        button.setText("+");
        box.getChildren().add(button);
        executionButton.add(button);

        EZMcfunction function = new EZMcfunction(EZMcfunctionType.MCFUNCTION);

        box.getChildren().add(function.pane);
        function.pane.setLayoutX(button.getBoundsInParent().getMaxX());

        defaultFunction = function;

        button.setOnMouseClicked(event -> {
            EZMcfunction function1 = new EZMcfunction(EZMcfunctionType.MCFUNCTION);
            StageManager.cont.nowEZTab.blockPane.getChildren().add(function1.pane);
            MainStageController.mouseFollowerFrom(function1);
            setSelectedBlock(function1);
        });
    }

    public static TeleportToEntity defaultTpEntity = null;
    public static TeleportToEntity nowTpEntity = null;

    private void buildTeleportToEntity()
    {
        HBox box = new HBox();
        entity.getChildren().add(box);
        Button button = new Button();
        button.setText("+");
        box.getChildren().add(button);
        defaultButton.add(button);

        TeleportToEntity tp = new TeleportToEntity();
        defaultTpEntity = tp;

        box.getChildren().add(tp.pane);
        tp.pane.setLayoutX(button.getBoundsInParent().getMaxX());
        button.setOnMouseClicked(event -> {
            TeleportToEntity tp1 = new TeleportToEntity();
            StageManager.cont.nowEZTab.blockPane.getChildren().add(tp1.pane);
            MainStageController.mouseFollowerFrom(tp1);
            if(selectedBlock.blockType == EZBlockType.onFunction)
                selectedBlock.parent.Blocks.add(0,tp1);
            else
                selectedBlock.parent.Blocks.add(selectedBlock.parent.Blocks.indexOf(selectedBlock)+1,tp1);
            tp1.parent.reassemble();
            setSelectedBlock(tp1);
        });
    }

    /*private void buildEffect()
    {

    }*/

    public static Kill defaultKill = null;
    public static Kill nowKill = null;

    private void buildKill()
    {
        HBox box = new HBox();
        entity.getChildren().add(box);
        Button button = new Button();
        button.setText("+");
        box.getChildren().add(button);
        defaultButton.add(button);

        Kill kill = new Kill();
        defaultKill = kill;

        box.getChildren().add(kill.pane);
        kill.pane.setLayoutX(button.getBoundsInParent().getMaxX());
        button.setOnMouseClicked(event -> {
            Kill kill1 = new Kill();
            StageManager.cont.nowEZTab.blockPane.getChildren().add(kill1.pane);
            MainStageController.mouseFollowerFrom(kill1);
            if(selectedBlock.blockType == EZBlockType.onFunction)
                selectedBlock.parent.Blocks.add(0,kill1);
            else
                selectedBlock.parent.Blocks.add(selectedBlock.parent.Blocks.indexOf(selectedBlock)+1,kill1);
            kill1.parent.reassemble();
            setSelectedBlock(kill1);
        });
    }

    public static AttributeModifierAdd defaultAttributeModifierAdd;
    public static AttributeModifierAdd nowAttributeModifierAdd;

    private void buildAttributeModifierAdd()
    {
        HBox box = new HBox();
        entity.getChildren().add(box);
        Button button = new Button();
        button.setText("+");
        box.getChildren().add(button);
        defaultButton.add(button);

        AttributeModifierAdd attribute = new AttributeModifierAdd();
        defaultAttributeModifierAdd = attribute;

        box.getChildren().add(attribute.pane);
        attribute.pane.setLayoutX(button.getBoundsInParent().getMaxX());
        button.setOnMouseClicked(event -> {
            AttributeModifierAdd attribute1 = new AttributeModifierAdd();
            StageManager.cont.nowEZTab.blockPane.getChildren().add(attribute1.pane);
            MainStageController.mouseFollowerFrom(attribute1);
            if(selectedBlock.blockType == EZBlockType.onFunction)
                selectedBlock.parent.Blocks.add(0,attribute1);
            else
                selectedBlock.parent.Blocks.add(selectedBlock.parent.Blocks.indexOf(selectedBlock)+1,attribute1);
            attribute1.parent.reassemble();
            setSelectedBlock(attribute1);
        });
    }

    public static EffectGive defaultEffectGive = null;
    public static EffectGive nowEffectGive = null;

    private void buildEffectGive()
    {
        HBox box = new HBox();
        entity.getChildren().add(box);
        Button button = new Button();
        button.setText("+");
        box.getChildren().add(button);
        defaultButton.add(button);

        EffectGive effect = new EffectGive();
        defaultEffectGive = effect;

        box.getChildren().add(effect.pane);
        effect.pane.setLayoutX(button.getBoundsInParent().getMaxX());
        button.setOnMouseClicked(event -> {
            EffectGive effect1 = new EffectGive();
            StageManager.cont.nowEZTab.blockPane.getChildren().add(effect1.pane);
            MainStageController.mouseFollowerFrom(effect1);
            if(selectedBlock.blockType == EZBlockType.onFunction)
                selectedBlock.parent.Blocks.add(0,effect1);
            else
                selectedBlock.parent.Blocks.add(selectedBlock.parent.Blocks.indexOf(selectedBlock)+1,effect1);
            effect1.parent.reassemble();
            setSelectedBlock(effect1);
        });
    }
}
