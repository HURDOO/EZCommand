package kr.kro.hurdoo.je1165;

import kr.kro.ezcommand.engine.thirdparty.plugin.EZJavaPlugin;
import kr.kro.hurdoo.je1165.type.*;
import kr.kro.hurdoo.je1165.type.Number;
import kr.kro.hurdoo.je1165.type.entity.Entity;
import kr.kro.hurdoo.je1165.type.entity.EntityParser;
import kr.kro.hurdoo.je1165.type.entity.EntityTypeSelector;
import kr.kro.hurdoo.je1165.type.rjtf.RawJsonTextFormat;

@SuppressWarnings("unused")
public class PluginMain extends EZJavaPlugin {
    @Override
    public void onEnable() {
        registerEZData("nbt", NBT.class, new NBTParser());
        registerEZData("entity", Entity.class,new EntityParser());

        registerEZBlockElement("boolean", BooleanLabel.class);
        registerEZBlockElement("number", Number.class);
        registerEZBlockElement("select", Select.class);
        registerEZBlockElement("string", StringLabel.class);

        registerEZBlockElement("raw_json_text_format", RawJsonTextFormat.class);
        registerEZBlockElement("entity_type_selector", EntityTypeSelector.class);
    }
}
