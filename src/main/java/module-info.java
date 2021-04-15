module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires json.simple;
    requires transitive javafx.web;
    requires transitive javafx.swing;

    requires org.scenicview.scenicview;
    requires org.controlsfx.controls;
    requires org.fxmisc.richtext;
    requires fonts.fontawesome;
    requires flowless;
    requires reactfx;
    requires org.yaml.snakeyaml;

    opens kr.kro.ezcommand to javafx.fxml;
    opens kr.kro.ezcommand.ui.fxml to javafx.fxml;

    exports kr.kro.ezcommand;
    exports kr.kro.ezcommand.ui;
    exports kr.kro.ezcommand.ui.fxml;
    exports kr.kro.ezcommand.engine.parser.type.rjtf;




    opens kr.kro.ezcommand.old.gui.a_selectProject to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.c_save to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.blocks.Attribute to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.blocks.effect to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.blocks.kill to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.blocks.onFunction to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.blocks.Teleport to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.blocks.weather to javafx.fxml;
    opens kr.kro.ezcommand.old.gui.b_main.specific to javafx.fxml;

    exports kr.kro.ezcommand.old.gui.a_selectProject;
    exports kr.kro.ezcommand.old.gui.b_main;
    exports kr.kro.ezcommand.old.gui.c_save;
    exports kr.kro.ezcommand.old.gui.b_main.blocks.Attribute;
    exports kr.kro.ezcommand.old.gui.b_main.blocks.effect;
    exports kr.kro.ezcommand.old.gui.b_main.blocks.kill;
    exports kr.kro.ezcommand.old.gui.b_main.blocks.onFunction;
    exports kr.kro.ezcommand.old.gui.b_main.blocks.Teleport;
    exports kr.kro.ezcommand.old.gui.b_main.blocks.weather;
    exports kr.kro.ezcommand.old.gui.b_main.specific;
}