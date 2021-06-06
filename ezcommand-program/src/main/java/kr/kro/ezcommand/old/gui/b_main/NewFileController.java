package kr.kro.ezcommand.old.gui.b_main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.gui.b_main.blocks.onFunction.onFunctionController;
import kr.kro.ezcommand.old.inner.EZTab;
import kr.kro.ezcommand.old.inner.EZTabType;

import java.net.URL;
import java.util.ResourceBundle;

public class NewFileController implements Initializable
{

    @FXML
    private Button createNewFolder;
    @FXML
    private ChoiceBox<String> FolderType;
    @FXML
    private TextField FolderName;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        FolderType.getItems().add("함수(커맨드) 파일 (.mcfunction)");
        createNewFolder.setOnMouseClicked(event -> {
            char[] name = FolderName.getText().toLowerCase().toCharArray();

            if(FolderType.getSelectionModel().getSelectedIndex() == 0) // EZFileType.MCFUNCTION
            {
                if(!onFunctionController.isFunctionName(FolderName.getText()))
                {
                    Alert nameErr = new Alert(Alert.AlertType.WARNING);
                    nameErr.setContentText("폴더의 이름이 다음 조건을 만족하지 못합니다:\n\n폴더 이름에는 소문자 알파벳과 숫자, _-. 만 들어갈 수 있습니다.");
                    nameErr.show();
                    return;
                }
                EZTab tab = new EZTab(EZTabType.MCFUNCTION,FolderName.getText());
                for(int j=0;j<BlockListGUIController.defaultButton.size();j++)
                {
                    BlockListGUIController.defaultButton.get(j).setDisable(true);
                }
            }
            StageManager.closeNewFileStage();
        });
    }
}
