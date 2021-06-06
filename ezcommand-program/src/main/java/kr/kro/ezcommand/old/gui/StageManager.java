package kr.kro.ezcommand.old.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.kro.ezcommand.old.gui.b_main.MainStageController;
import kr.kro.ezcommand.old.inner.EZProject;

import java.io.IOException;

public class StageManager
{
    private static Stage FirstPage = new Stage();
    public static void createFirstPage(Stage primaryStage) throws IOException {
        FirstPage = primaryStage;
        //Parent root = FXMLLoader.load(Objects.requireNonNull(StageManager.class.getClassLoader().getResource("kr/kro/ezcommand/gui/a_selectProject/FirstGUI.fxml")));
        Parent root = FXMLLoader.load(StageManager.class.getResource("/kr/kro/ezcommand/gui/a_selectProject/FirstGUI.fxml"));

        FirstPage.setScene(new Scene(root/*new Label("Hello Wrodl!")*/, 600, 350));
        FirstPage.setTitle(ProgramInfo.ProgramName + " " + ProgramInfo.version);
        FirstPage.setResizable(false);
        FirstPage.show();
    }

    private static Stage NewProjectStage = new Stage();
    public static void createNewProjectStage() throws IOException {
        Parent root = FXMLLoader.load(StageManager.class.getResource("/kr/kro/ezcommand/gui/a_selectProject/NewProject.fxml"));
        NewProjectStage.setScene(new Scene(root, 400, 200));
        NewProjectStage.setTitle("새 프로젝트 만들기" + " - " + ProgramInfo.ProgramName + " " + ProgramInfo.version);
        NewProjectStage.setResizable(false);
        NewProjectStage.initModality(Modality.WINDOW_MODAL);
        NewProjectStage.initOwner(FirstPage);
        NewProjectStage.setOnHiding(event -> {
            NewProjectStage.close();
            NewProjectStage = new Stage();
        });
        NewProjectStage.show();
    }

    private static Stage MainStage = new Stage();
    public static void createMainStage() throws IOException {
        FXMLLoader MainStageLoader = new FXMLLoader(StageManager.class.getResource("/kr/kro/ezcommand/gui/b_main/MainStage.fxml"));
        MainStage.setScene(new Scene(MainStageLoader.load(),1200,800));

        cont = MainStageLoader.getController();

        MainStage.setTitle(EZProject.ProjectName + " - " + ProgramInfo.ProgramName + " " + ProgramInfo.version);
        FirstPage.close();
        FirstPage = new Stage();
        NewProjectStage.hide();
        NewProjectStage = new Stage();

        MainStage.show();

        FXMLLoader BlockListLoader = new FXMLLoader(StageManager.class.getResource("/kr/kro/ezcommand/gui/b_main/BlockListGUI.fxml"));
        EZProject.BlockListGUI = BlockListLoader.load();
    }

    public static MainStageController cont;

    private static Stage NewFileStage = new Stage();
    public static void createNewFileStage() throws IOException {
        Parent root = FXMLLoader.load(StageManager.class.getResource("/kr/kro/ezcommand/gui/b_main/NewFile.fxml"));
        NewFileStage.setScene(new Scene(root, 600, 400));
        NewFileStage.setTitle("새 폴더 만들기" + " - " + EZProject.ProjectName + " (" + ProgramInfo.ProgramName + " " + ProgramInfo.version + ")");
        NewFileStage.setResizable(false);
        NewFileStage.initModality(Modality.WINDOW_MODAL);
        NewFileStage.initOwner(MainStage);
        NewFileStage.setOnHiding(event -> {
            NewFileStage.close();
            NewFileStage = new Stage();
        });
        NewFileStage.show();
    }

    public static void closeNewFileStage() {
        NewFileStage.hide();
    }

    public static Stage datapackConvertionStage = new Stage();
    public static void createDatapackConvertionStage() throws IOException {
        Parent root = FXMLLoader.load(StageManager.class.getResource("/kr/kro/ezcommand/gui/c_save/toDatapack.fxml"));
        datapackConvertionStage.setScene(new Scene(root, 600, 400));
        datapackConvertionStage.setTitle("데이터 팩으로 변환" + " - " + EZProject.ProjectName + " (" + ProgramInfo.ProgramName + " " + ProgramInfo.version + ")");
        datapackConvertionStage.setResizable(false);
        datapackConvertionStage.initModality(Modality.WINDOW_MODAL);
        datapackConvertionStage.initOwner(MainStage);
        datapackConvertionStage.setOnHiding(event -> {
            datapackConvertionStage.close();
            datapackConvertionStage = new Stage();
        });
        datapackConvertionStage.show();
    }

    public static void closeDatapackConvertionStage() {
        datapackConvertionStage.hide();
    }

    public static Stage specificAttributeModifier = new Stage();
    public static void createSpecificAttributeModifier() throws IOException {
        Parent root = FXMLLoader.load(StageManager.class.getResource("/kr/kro/ezcommand/gui/b_main/specific/SpecificAttributeModifier.fxml"));
        specificAttributeModifier.setScene(new Scene(root, 400, 300));
        specificAttributeModifier.setTitle("변경자 속성 수정" + " - " + EZProject.ProjectName + " (" + ProgramInfo.ProgramName + " " + ProgramInfo.version + ")");
        specificAttributeModifier.setResizable(false);
        specificAttributeModifier.initModality(Modality.WINDOW_MODAL);
        specificAttributeModifier.initOwner(MainStage);
        specificAttributeModifier.setOnHiding(event -> {
            specificAttributeModifier.close();
            specificAttributeModifier = new Stage();
        });
        specificAttributeModifier.show();
    }

    public static void closeSpecificAttributeModifier() {
        specificAttributeModifier.hide();
    }
}
