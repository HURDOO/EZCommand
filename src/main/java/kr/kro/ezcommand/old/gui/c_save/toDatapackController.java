package kr.kro.ezcommand.old.gui.c_save;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.inner.EZBlock;
import kr.kro.ezcommand.old.inner.EZMcfunction;
import kr.kro.ezcommand.old.inner.EZProject;
import kr.kro.ezcommand.old.inner.EZTab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class toDatapackController implements Initializable
{
    @FXML TextField name;
    @FXML TextField description;
    @FXML TextField path;
    @FXML Button findPath;
    @FXML Button convert;

    File savePath;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        name.setText(EZProject.ProjectName);
        description.setText("Made with EZCommand.");
        try {
            savePath = new File(System.getenv("appdata") + File.separator + ".minecraft" + File.separator + "saves");
        } catch(NullPointerException e)
        {
            savePath = new File("/");
        }
        path.setText(savePath.getAbsolutePath());
        findPath.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setInitialDirectory(new File(savePath.getAbsolutePath()));

            File selected = chooser.showDialog(StageManager.datapackConvertionStage);
            /*
            if(selected == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("해당 폴더를 찾을 수 없습니다:\n");
                alert.show();
            }
             */
            if(selected != null)
            {
                savePath = selected;
                path.setText(savePath.getAbsolutePath());
            }
        });

        convert.setOnAction(event -> {

            // create root folder

            File rootFolder = new File(savePath + File.separator + name.getText());
            if(rootFolder.exists())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("이미 해당 이름의 데이터 팩이 존재합니다.\n지정된 이름: " + name.getText());
                alert.show();
                return;
            }
            try {
                rootFolder.mkdir();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            // create pack.mcmeta

            File pack_mcmeta = new File(rootFolder.getAbsolutePath() + File.separator + "pack.mcmeta");
            try {
                pack_mcmeta.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                PrintWriter writer = new PrintWriter(pack_mcmeta);
                writer.println("{");
                writer.println("  \"pack\": {");
                writer.println("    \"pack_format\": 6,");
                writer.println("    \"description\": \"" + description.getText() + "\"");
                writer.println("  }");
                writer.println("}");
                writer.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // create data folder

            File dataFolder = new File(rootFolder.getAbsolutePath() + File.separator + "data");
            try {
                dataFolder.mkdir();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            // now, work per each tabs

            for(int i=0;i<EZProject.ezTabs.size();i++)
            {
                // create new tab folder
                EZTab tab = EZProject.ezTabs.get(i);
                File tabFolder = new File(dataFolder.getAbsolutePath() + File.separator + tab.name + File.separator + "functions");

                if(!tabFolder.mkdirs())
                {
                    System.out.println("didn't even made!");
                }

                if(tabFolder == null)
                {
                    System.out.println("Null..");
                }

                for (int j = 0; j < tab.Executions.size(); j++)
                {
                    // create new .mcfunction file

                    EZMcfunction function = tab.Executions.get(j);
                    //System.out.println(tabFolder.getPath() + File.separator + function.name + ".mcfunction");
                    File mcfunction = new File(tabFolder.getPath() + File.separator + function.name + ".mcfunction");
                    try {
                        mcfunction.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    PrintWriter writer = null;

                    try {
                        writer = new PrintWriter(mcfunction);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    // add commands to .mcfunction file

                    for(int k=0;k<function.Blocks.size();k++)
                    {
                        EZBlock block = function.Blocks.get(k);
                        writer.println(block.toString());
                    }
                    writer.close();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("데이터팩 추출이 완료되었습니다.\n데이터팩 이름: " + name.getText()  + "\n저장된 폴더: " + savePath.getAbsolutePath());
            alert.setOnCloseRequest(event1 -> {
                StageManager.closeDatapackConvertionStage();
            });
            alert.show();
        });
    }
}
