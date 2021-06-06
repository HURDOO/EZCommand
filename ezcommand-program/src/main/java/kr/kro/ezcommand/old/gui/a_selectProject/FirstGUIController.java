package kr.kro.ezcommand.old.gui.a_selectProject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import kr.kro.ezcommand.old.gui.ProgramInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import static kr.kro.ezcommand.old.gui.ProgramInfo.ProgramName;
import static kr.kro.ezcommand.old.gui.StageManager.createNewProjectStage;

public class FirstGUIController implements Initializable {
    @FXML
    private Button newProject;
    @FXML
    private Text EZCommand;
    @FXML
    private Text version;

    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        EZCommand.setText(ProgramName);
        version.setText(ProgramInfo.version);
        newProject.setOnMouseClicked( event -> {
            try {
                createNewProjectStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            if(isLatest()) System.out.println("Yes!");
            else System.out.println("No!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private boolean isLatest() throws IOException {
        URL url = new URL("http://latest.ezcommand.kro.kr");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        //conn.setDoInput(true);
        //conn.setDoOutput(true);
        //OutputStream stream = conn.getOutputStream();
        //stream.write();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "EUC_KR"));
        String line =  reader.readLine();
        System.out.println(line);
        if(reader.readLine()!=null) throw new NullPointerException("more than 1 line!");
        if(ProgramInfo.version == line) return true;
        return false;
        // [출처] [파싱] 1. 기초적인 웹 데이터 받아오기|작성자 occidere
    }

}
