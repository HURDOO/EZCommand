package kr.kro.ezcommand.old.gui.b_main.blocks.kill;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.inner.EZBlocks.Kill;
import kr.kro.ezcommand.old.inner.Types.Selector;

import java.net.URL;
import java.util.ResourceBundle;

import static kr.kro.ezcommand.old.gui.b_main.BlockListGUIController.defaultTpEntity;
import static kr.kro.ezcommand.old.inner.Types.Selector.selectorList.playerNickname;
import static kr.kro.ezcommand.old.inner.Types.Selector.setSelectorList;

public class KillController implements Initializable
{
    @FXML ComboBox target;

    private Kill thisKill;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        thisKill = BlockListGUIController.nowKill;
        target.setPromptText("엔티티");
        setSelectorList(target.getItems());

        switch (thisKill.target) {
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
        /*target.getEditor().textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                switch (target.getEditor().getText()) {
                    case krAllEntity:
                        thisKill.target = allEntity;
                        thisKill.targetPlayerName = null;
                        break;
                    case krAllPlayer:
                        thisKill.target = allPlayer;
                        thisKill.targetPlayerName = null;
                        break;
                    case krRandomPlayer:
                        thisKill.target = randomPlayer;
                        thisKill.targetPlayerName = null;
                        break;
                    case krNearesetPlayer:
                        thisKill.target = nearestPlayer;
                        thisKill.targetPlayerName = null;
                        break;
                    case krExecutor:
                        thisKill.target = executor;
                        thisKill.targetPlayerName = null;
                        break;
                    default:
                        if(isNickName(target.getEditor().getText()))
                        {
                            thisKill.target = playerNickname;
                            thisKill.targetPlayerName = target.getEditor().getText();
                        }
                        else
                        {
                            target.getEditor().setText(thisKill.targetPlayerName);
                        }
                        break;
                }
                //infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());
            }
        });*/
        target.getEditor().textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(Selector.selectorList.valueOf(target.getEditor().getText()) != null)
                {
                    thisKill.target = Selector.selectorList.valueOf(target.getEditor().getText());
                    thisKill.targetPlayerName = null;
                }
                else
                {
                    if(Selector.isNickName(target.getEditor().getText()))
                    {
                        thisKill.target = playerNickname;
                        thisKill.targetPlayerName = target.getEditor().getText();
                    }
                    else if(thisKill.target == playerNickname)
                    {
                        target.getEditor().setText(thisKill.targetPlayerName);
                    }
                    else
                    {
                        thisKill.target = playerNickname;
                        thisKill.targetPlayerName = "Player";
                        target.getEditor().setText(thisKill.targetPlayerName);
                    }
                }
                //infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());
            }
        });
    }
}
