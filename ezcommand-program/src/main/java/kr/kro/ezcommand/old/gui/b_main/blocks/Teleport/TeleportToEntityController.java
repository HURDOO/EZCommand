package kr.kro.ezcommand.old.gui.b_main.blocks.Teleport;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.old.inner.EZBlocks.TeleportToEntity;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static kr.kro.ezcommand.old.gui.b_main.BlockListGUIController.*;
import static kr.kro.ezcommand.old.inner.Types.Selector.selectorList.playerNickname;
import static kr.kro.ezcommand.old.inner.Types.Selector.setSelectorList;

public class TeleportToEntityController implements Initializable
{

    @FXML ComboBox target;
    @FXML ComboBox destination;

    private TeleportToEntity thisTp;

    public static AnchorPane getTeleportToEntityFXML() throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(TeleportToEntityController.class.getClassLoader().getResource("kr/kro/ezcommand/gui/b_main/blocks/Teleport/TeleportToEntity.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        thisTp = nowTpEntity;
        target.setPromptText("엔티티");
        setSelectorList(target.getItems());
        destination.setPromptText("엔티티");
        setSelectorList(destination.getItems());

        switch (thisTp.target) {
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
                    thisTp.target = Selector.selectorList.valueOf(target.getEditor().getText());
                    thisTp.targetPlayerName = null;
                }
                else
                {
                    if(Selector.isNickName(target.getEditor().getText()))
                    {
                        thisTp.target = playerNickname;
                        thisTp.targetPlayerName = target.getEditor().getText();
                    }
                    else if(thisTp.target == playerNickname)
                    {
                        target.getEditor().setText(thisTp.targetPlayerName);
                    }
                    else
                    {
                        thisTp.target = playerNickname;
                        thisTp.targetPlayerName = "Player";
                        target.getEditor().setText(thisTp.targetPlayerName);
                    }
                }
                //infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());
            }
        });
        switch (thisTp.destination) {
            case allEntity:
                destination.getSelectionModel().select(0); // @e
                break;
            case allPlayer:
                destination.getSelectionModel().select(1); // @a
                break;
            case randomPlayer:
                destination.getSelectionModel().select(2); // @r
                break;
            case nearestPlayer:
                destination.getSelectionModel().select(3); // @p
                break;
            case executor:
                destination.getSelectionModel().select(4); // @s
                break;
            case playerNickname:
                destination.setAccessibleText(defaultTpEntity.destinationPlayerName);
                break;
            default:
                throw new NullPointerException("Out of Enum \"selectionList\"");
        }
        destination.getEditor().textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(Selector.selectorList.valueOf(destination.getEditor().getText()) != null)
                {
                    thisTp.destination = Selector.selectorList.valueOf(destination.getEditor().getText());
                    thisTp.destinationPlayerName = null;
                }
                else
                {
                    if(Selector.isNickName(destination.getEditor().getText()))
                    {
                        thisTp.destination = playerNickname;
                        thisTp.destinationPlayerName = destination.getEditor().getText();
                    }
                    else if(thisTp.destination == playerNickname)
                    {
                        destination.getEditor().setText(thisTp.destinationPlayerName);
                    }
                    else
                    {
                        thisTp.destination = playerNickname;
                        thisTp.destinationPlayerName = "Player";
                        target.getEditor().setText(thisTp.destinationPlayerName);
                    }
                }
                //infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());
            }
        });
    }
}
