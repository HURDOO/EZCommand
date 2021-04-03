package kr.kro.ezcommand;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kr.kro.ezcommand.engine.parser.FileParser;
import kr.kro.ezcommand.ui.stage.MainStage;

public class Main extends Application
{
    public static Font JALNAN = null;
    public static Font FONTAWESOME = null;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행
        JALNAN = Font.loadFont(FileParser.class.getResourceAsStream("/font/Jalnan.ttf"),10);
        FONTAWESOME = Font.loadFont(FileParser.class.getResourceAsStream("/font/Font Awesome 5 Free-Solid-900.otf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothic.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothic_Italic.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothicBold.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothicBold_Italic.ttf"),10);
        MainStage.create();
    }
}
