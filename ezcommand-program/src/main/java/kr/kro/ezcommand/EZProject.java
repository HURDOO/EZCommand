package kr.kro.ezcommand;

import kr.kro.ezcommand.engine.thirdparty.EZPack;

import java.util.ArrayList;
import java.util.List;

public class EZProject {
    private static String name;
    private static String writtenVersion;

    private static List<EZPack> packs;

    public static void renew(String name, String writtenVersion) {
        EZProject.name = name;
        EZProject.writtenVersion = writtenVersion;

        packs = new ArrayList<>();
    }

    public static String getName() {
        return name;
    }
    public static String getWrittenVersion() {
        return writtenVersion;
    }
    public static List<EZPack> getPacks() {
        return packs;
    }
}
