package kr.kro.ezcommand.engine.thirdparty.plugin;

import kr.kro.ezcommand.EZCommand;
import kr.kro.ezcommand.engine.parser.EZBlockElement;

public class EZJavaPlugin {

    public void onEnable() {

    }
    public void onDisable() {

    }

    public final void registerEZBlockElement(String id,Class<? extends EZBlockElement> elementClass) {
        EZCommand.EZBlockElements.put(id,elementClass);
    }
}
