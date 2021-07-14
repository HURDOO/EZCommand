package kr.kro.ezcommand.engine.thirdparty.plugin;

import kr.kro.ezcommand.EZCommand;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.file.EZData;
import kr.kro.ezcommand.engine.parser.file.EZDataParser;

public class EZJavaPlugin {

    public void onEnable() {

    }
    public void onDisable() {

    }

    public final void registerEZBlockElement(String id,Class<? extends EZBlockElement> elementClass) {
        EZCommand.EZBlockElements.put(id,elementClass);
    }

    public final void registerEZData(String name,Class<? extends EZData> dataClass, EZDataParser parser) {
        EZCommand.datas.put(name,dataClass);
        EZCommand.dataParsers.put(name,parser);
    }
}
