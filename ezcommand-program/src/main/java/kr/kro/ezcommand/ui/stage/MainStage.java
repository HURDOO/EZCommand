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

import java.io.IOException;
import java.util.Objects;

public class MainStage
{
    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;

    public static void create() {
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
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(0,0.001);
        pane.setCenter(splitPane);

        splitPane.getItems().add(BlockList.getUi());

        /*Pane*/
        Pane blockWorkspace = new Pane();
        splitPane.getItems().add(blockWorkspace);

        try {
            pane.setTop(FXMLLoader.load(Objects.requireNonNull(MainStage.class.getResource("/fxml/MainStage_MenuBar.fxml"))));
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


        FileLoader.loadEZPlugins();
        FileLoader.loadEZPacks();
    }

    public static Pane backPane = new Pane();
}
