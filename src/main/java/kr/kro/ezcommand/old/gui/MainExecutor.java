package kr.kro.ezcommand.old.gui;

import kr.kro.ezcommand.old.gui.FirstGUICreator;

public class MainExecutor {
    public static void main(String[] args) {
        //System.out.println(MainExecutor.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("now: " + System.getProperty("user.dir"));
        FirstGUICreator.main(args);
    }
}
