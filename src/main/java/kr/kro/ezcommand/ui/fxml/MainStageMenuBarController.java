package kr.kro.ezcommand.ui.fxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import kr.kro.ezcommand.engine.EZTab;

import java.net.URL;
import java.util.ResourceBundle;

public class MainStageMenuBarController implements Initializable {

    @FXML MenuItem button_toCommand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_toCommand.setOnAction(event -> {
            System.out.println(EZTab.nowTab.toCommand());
        });
    }
}
