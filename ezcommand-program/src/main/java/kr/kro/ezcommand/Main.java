package kr.kro.ezcommand;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kr.kro.ezcommand.engine.parser.file.FileParser;
import kr.kro.ezcommand.ui.stage.MainStage;

public class Main extends Application
{
    public static Font FONT = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        EZCommand.renew("Test","Alpha-0.2");

        System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행
        Font.loadFont(FileParser.class.getResourceAsStream("/font/Jalnan.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothic.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothic_Italic.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothicBold.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothicBold_Italic.ttf"),10);

        FONT = Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumGothicCoding-2.5/NanumGothicCoding-Bold.ttf"),10);
        MainStage.create();
    }
}
