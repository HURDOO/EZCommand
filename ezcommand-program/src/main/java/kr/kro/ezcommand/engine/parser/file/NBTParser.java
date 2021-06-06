package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.NBT;
import org.json.simple.JSONObject;

public class NBTParser {
    public static NBT parse(JSONObject object) {
        String name = object.get("name").toString();
        String parse = object.get("parse").toString();

        NBT nbt = new NBT(name,parse);

        EZBlockElement element = EZBlockElementParser.parse(nbt,"nbt",(JSONObject) object.get("value"));
        nbt.setValue(element);

        return nbt;
    }
}
