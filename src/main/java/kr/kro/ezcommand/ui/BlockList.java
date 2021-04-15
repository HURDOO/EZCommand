package kr.kro.ezcommand.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.file.FileLoader;
import kr.kro.ezcommand.engine.parser.file.FileParser;
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

    private static final List<EZBlock> blocks = new ArrayList<>();
    public static List<EZBlock> getBlocks() {
        return blocks;
    }

    public static void addExampleBlock(EZBlock block) {
        block.setExampleBlock(true);

        BlockList.getBlocks().add(block);
        BlockList.getUi().getChildren().add(block.getUi());
    }
}
