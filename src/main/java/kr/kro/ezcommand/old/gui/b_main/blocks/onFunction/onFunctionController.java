package kr.kro.ezcommand.old.gui.b_main.blocks.onFunction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.inner.EZMcfunction;

import java.net.URL;
import java.util.ResourceBundle;

public class onFunctionController implements Initializable
{
    private EZMcfunction thisFunction = null;

    @FXML TextField functionNameGUI;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        thisFunction = BlockListGUIController.nowFunction;
        functionNameGUI.setText(thisFunction.name);
        /*try {
            if (functionNameExists(functionNameGUI.getText()))
                functionNameGUI.setStyle("-fx-text-fill: black;");
            else
                functionNameGUI.setStyle("-fx-text-fill: red;");
        } catch (NullPointerException e) {

            functionNameGUI.setStyle("-fx-text-fill: black;");
        }*/
        functionNameGUI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(isFunctionName(functionNameGUI.getText()))
                {
                    thisFunction.name = functionNameGUI.getText();
                    functionNameGUI.setStyle("-fx-text-fill: black;");
                }
                else
                {
                    functionNameGUI.setStyle("-fx-text-fill: red;");
                }
            }
        });
    }

    public static boolean isFunctionName(String name)
    {
        char[] name1 = name.toCharArray();
        for(int i=0;i<name1.length;i++)
        {
            if(!(name1[i]=='_' || name1[i]=='.' || name1[i]=='-' || ('0'<=name1[i] && name1[i]<='9') || ('a'<=name1[i] && name1[i]<='z')))
                return false;
        }
        return true;
    }

    /*private boolean functionNameExists(String name)
    {
        for(int i=0;i<StageManager.cont.nowEZTab.Blocks.size();i++)
        {
            if(StageManager.cont.nowEZTab.Blocks.get(i) == thisFunction) continue;
            if(StageManager.cont.nowEZTab.Blocks.get(i).name.toLowerCase().equals(name.toLowerCase()))
                return false;
        }
        return true;
    }*/
}
