package kr.kro.ezcommand.old.gui.b_main.blocks.Attribute;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.inner.Types.Attribute;
import kr.kro.ezcommand.old.inner.EZBlocks.AttributeModifierAdd;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static kr.kro.ezcommand.old.gui.b_main.BlockListGUIController.defaultTpEntity;
import static kr.kro.ezcommand.old.inner.Types.Selector.selectorList.playerNickname;

public class AttributeModifierAddController implements Initializable
{
    @FXML ComboBox target;
    @FXML ChoiceBox attribute;
    @FXML Button act;

    AttributeModifierAdd thisAttribute = BlockListGUIController.nowAttributeModifierAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        target.setPromptText("엔티티");
        //setSelectorList(target.getItems());
        Attribute.setAttributeList(attribute.getItems());

        switch (thisAttribute.target) {
            case allEntity:
                target.getSelectionModel().select(0); // @e
                break;
            case allPlayer:
                target.getSelectionModel().select(1); // @a
                break;
            case randomPlayer:
                target.getSelectionModel().select(2); // @r
                break;
            case nearestPlayer:
                target.getSelectionModel().select(3); // @p
                break;
            case executor:
                target.getSelectionModel().select(4); // @s
                break;
            case playerNickname:
                target.setAccessibleText(defaultTpEntity.targetPlayerName);
                break;
            default:
                throw new NullPointerException("Out of Enum \"selectionList\"");
        }
        target.getEditor().textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(Selector.selectorList.valueOf(target.getEditor().getText()) != null)
                {
                    thisAttribute.target = Selector.selectorList.valueOf(target.getEditor().getText());
                    thisAttribute.targetPlayerName = null;
                }
                else
                {
                    if(Selector.isNickName(target.getEditor().getText()))
                    {
                        thisAttribute.target = playerNickname;
                        thisAttribute.targetPlayerName = target.getEditor().getText();
                    }
                    else if(thisAttribute.target == playerNickname)
                    {
                        target.getEditor().setText(thisAttribute.targetPlayerName);
                    }
                    else
                    {
                        thisAttribute.target = playerNickname;
                        thisAttribute.targetPlayerName = "Player";
                        target.getEditor().setText(thisAttribute.targetPlayerName);
                    }
                }
                //infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());
            }
        });

        attribute.getSelectionModel().select(thisAttribute.attribute.ordinal());
        attribute.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                thisAttribute.attribute = Attribute.AttributeList.values()[attribute.getSelectionModel().getSelectedIndex()];
            }
        });

        act.setOnAction(event -> {
            BlockListGUIController.nowAttributeModifierAdd = thisAttribute;
            try {
                StageManager.createSpecificAttributeModifier();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        modifierChanged();
    }

    public void modifierChanged()
    {
        act.setText(thisAttribute.name + " (" + thisAttribute.amount + "만큼 " + thisAttribute.modifierType.toString() + ")");

        attribute.getSelectionModel().select(thisAttribute.attribute.ordinal());
    }

}
