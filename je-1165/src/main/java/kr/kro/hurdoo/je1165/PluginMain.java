package kr.kro.hurdoo.je1165;

import kr.kro.ezcommand.engine.thirdparty.plugin.EZJavaPlugin;
import kr.kro.hurdoo.je1165.type.BooleanLabel;
import kr.kro.hurdoo.je1165.type.Number;
import kr.kro.hurdoo.je1165.type.Select;
import kr.kro.hurdoo.je1165.type.StringLabel;
import kr.kro.hurdoo.je1165.type.rjtf.RawJsonTextFormat;

public class PluginMain extends EZJavaPlugin {
    @Override
    public void onEnable() {
        registerEZBlockElement("boolean", BooleanLabel.class);
        registerEZBlockElement("number", Number.class);
        registerEZBlockElement("select", Select.class);
        registerEZBlockElement("string", StringLabel.class);

        registerEZBlockElement("raw_json_text_format", RawJsonTextFormat.class);
    }
}
