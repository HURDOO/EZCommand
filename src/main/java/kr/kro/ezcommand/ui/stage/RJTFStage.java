package kr.kro.ezcommand.ui.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.kro.ezcommand.engine.parser.type.rjtf.RawJsonTextFormat;
import kr.kro.ezcommand.ui.fxml.RJTFTextEditorController;

import java.io.IOException;

public class RJTFStage {
    private static Stage stage = new Stage();

    public static RJTFTextEditorController getController() {
        return controller;
    }
    private static RJTFTextEditorController controller;

    static {
        FXMLLoader loader = new FXMLLoader(RawJsonTextFormat.class.getResource("/fxml/RawJsonTextFormat_TextEditor.fxml"));
        BorderPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(pane));
        controller = loader.getController();

        stage.setTitle("JSON 문구 수정");
        stage.setResizable(false);
        try {
            // run
            stage.getIcons().add(new Image("/src/main/resources/icons/CommandBlock.png"));
        } catch (IllegalArgumentException e) {
            // jar
            stage.getIcons().add(new Image("/icons/CommandBlock.png"));
        }
        stage.initOwner(MainStage.getStage());

        stage.setOnCloseRequest(event -> {
            controller.save();
            controller.getNow().updateText();
        });
    }

    public static void show()
    {
        stage.show();
    }
}
