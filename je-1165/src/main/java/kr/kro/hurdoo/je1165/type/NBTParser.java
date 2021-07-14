package kr.kro.hurdoo.je1165.type;

import com.google.gson.JsonObject;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.file.EZBlockElementParser;
import kr.kro.ezcommand.engine.parser.file.EZDataParser;

public class NBTParser implements EZDataParser {
    public NBT parse(JsonObject object) {
        String name = object.get("name").getAsString();
        String parse = object.get("parse").getAsString();

        NBT nbt = new NBT(name,parse);

        EZBlockElement element = EZBlockElementParser.parse(nbt,"nbt", object.get("value").getAsJsonObject());
        nbt.setValue(element);

        return nbt;
    }
}
