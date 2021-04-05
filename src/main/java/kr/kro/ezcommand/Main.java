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
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothic.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothic_Italic.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothicBold.ttf"),10);
        Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumFontSetup_TTF_BARUNGOTHIC/NanumBarunGothicBold_Italic.ttf"),10);

        Font nanumCoding = Font.loadFont(FileParser.class.getResourceAsStream("/font/NanumGothicCoding-2.5/NanumGothicCoding-Bold.ttf"),10);
        System.out.println(nanumCoding.getFamily());
        MainStage.create();
    }
}
