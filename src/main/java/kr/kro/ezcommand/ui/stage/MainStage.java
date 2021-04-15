package kr.kro.ezcommand.ui.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import kr.kro.ezcommand.engine.EZTab;
import kr.kro.ezcommand.engine.parser.file.FileLoader;
import kr.kro.ezcommand.ui.BlockList;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainStage
{
    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;
    private static SplitPane splitPane;
    public static void create() throws FileNotFoundException {
        stage = new Stage();
        try {
            // run
            stage.getIcons().add(new Image("/src/main/resources/icons/CommandBlock.png"));
        } catch (IllegalArgumentException e) {
            // jar
            stage.getIcons().add(new Image("/icons/CommandBlock.png"));
        }
        stage.setWidth(1000);
        stage.setWidth(600);

        BorderPane pane = new BorderPane();
        splitPane = new SplitPane();
        splitPane.setDividerPosition(0,0.001);
        pane.setCenter(splitPane);

        FileLoader.loadEZPacks();
        splitPane.getItems().add(BlockList.getUi());

        Pane blockWorkspace = new Pane();
        splitPane.getItems().add(blockWorkspace);

        try {
            pane.setTop(FXMLLoader.load(MainStage.class.getResource("/fxml/MainStage_MenuBar.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // temp variables only for test.
        EZTab tab = new EZTab();
        EZTab.nowTab = tab;
        blockWorkspace.getChildren().add(new TabPane(tab.getUi()));
        tab.getUiPane().setPrefSize(1000,600);
        tab.getUi().setClosable(false);

        backPane.getChildren().add(pane);
        Scene scene = new Scene(backPane);
        //scene.getStylesheets().add("/src/main/resources/css/bootstrap3.css");
        stage.setScene(scene);
        stage.show();

        //ScenicView.show(scene);
    }

    public static Pane backPane = new Pane();
    public static double getSeparatorPosition() {
        double div = splitPane.getDividers().get(0).getPosition();
        double width = stage.getWidth();
        return width*div;
    }
}
