package kr.kro.ezcommand;

import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.thirdparty.EZPack;
import kr.kro.ezcommand.engine.thirdparty.plugin.EZJavaPlugin;
import kr.kro.ezcommand.engine.thirdparty.plugin.EZPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EZCommand {
    public static String EZPluginFolder = "D:\\1_Coding\\EZCommand\\je-1165\\build\\libs\\";

    // Project
    public static String name;
    public static String version;

    public static HashMap<String,Class<? extends EZBlockElement>> EZBlockElements = new HashMap<>();
    public static List<EZBlock> EZBlocks = new ArrayList<>();

    public static List<EZPlugin> plugins = new ArrayList<>();
    public static List<EZPack> packs = new ArrayList<>();

    public static void renew(String name, String version) {
        EZCommand.name = name;
        EZCommand.version = version;
    }
}
