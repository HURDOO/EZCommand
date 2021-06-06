package kr.kro.ezcommand.old.gui.a_selectProject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import kr.kro.ezcommand.old.gui.ProgramInfo;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.inner.EZProject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static kr.kro.ezcommand.old.inner.EZProject.*;


public class NewProjectController implements Initializable {
    @FXML
    private TextField ProjectName;
    @FXML
    private Button createNewProject;
    @FXML
    private ChoiceBox selectEdition;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        selectEdition.getItems().add("Java Edition");
        selectEdition.getItems().add("Bedrock Edition");
        selectEdition.getSelectionModel().select(0);
        createNewProject.setOnMouseClicked(event -> {
            EZProject.ProjectName = this.ProjectName.getText();
            EZProject.ProjectVersion = ProgramInfo.innerVersion;
            switch(selectEdition.getSelectionModel().getSelectedIndex())
            {
                case 0: // Java
                    EZProject.Edition = MCEdition.JAVA;
                    break;
                case 1: // Bedrock
                    EZProject.Edition = MCEdition.BEDROCK;
                    break;
                default:
                    throw new NullPointerException("Out of Edition: " + selectEdition.getSelectionModel().getSelectedIndex());
            }
            try {
                StageManager.createMainStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
