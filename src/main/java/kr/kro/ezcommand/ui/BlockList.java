package kr.kro.ezcommand.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.FileParser;
import kr.kro.ezcommand.ui.stage.MainStage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockList {
    private static VBox ui = new VBox();
    public static VBox getUi() {
        return ui;
    }
    static {
        ui.setPadding(new Insets(10,10,10,10));
        ui.setSpacing(10);
    }

    private static List<EZBlock> blocks = new ArrayList<>();

    public static void loadBlocks() {

        try {
            loadBlock("/files/weather.json");
            loadBlock("/files/title.json");
            loadBlock("/files/datapack_enable_disable.json");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void loadBlock(String url) throws IOException, ParseException {
        EZBlock block = FileParser.parse(BlockList.class.getResourceAsStream(url));
        block.setAsExampleBlock();

        blocks.add(block);
        ui.getChildren().add(block.getUi());
    }

}
