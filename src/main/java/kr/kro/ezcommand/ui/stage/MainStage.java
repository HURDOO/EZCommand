package kr.kro.ezcommand.ui.stage;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kr.kro.ezcommand.engine.EZTab;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.FileParser;
import kr.kro.ezcommand.ui.BlockList;
import org.json.simple.parser.ParseException;
import org.scenicview.ScenicView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainStage
{
    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;
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

        AnchorPane blockWorkspace = new AnchorPane();
        pane.setCenter(blockWorkspace);

        BlockList.loadBlocks();
        pane.setLeft(BlockList.getUi());

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

        backPane.getChildren().add(pane);
        Scene scene = new Scene(backPane);
        stage.setScene(scene);
        stage.show();

        //ScenicView.show(scene);
    }

    public static Pane backPane = new Pane();
}
