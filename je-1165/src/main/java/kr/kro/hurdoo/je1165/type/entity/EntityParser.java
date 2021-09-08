package kr.kro.hurdoo.je1165.type.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kr.kro.ezcommand.engine.parser.file.EZData;
import kr.kro.ezcommand.engine.parser.file.EZDataParser;
import kr.kro.hurdoo.je1165.type.NBT;

import java.util.LinkedList;
import java.util.List;

public class EntityParser implements EZDataParser {

    @Override
    public EZData parse(JsonObject object) {
        String name = object.get("name").getAsString();
        String parse = object.get("parse").getAsString();
        JsonArray nbtArray = object.get("nbt").getAsJsonArray();
        boolean isRealEntity = object.get("isRealEntity").getAsBoolean();

        List<NBT> nbtList = new LinkedList<>();
        for(JsonElement e : nbtArray) {
            String nbtName = e.getAsString();
            NBT nbt = NBT.nbtMap.get(nbtName);
            nbtList.add(nbt);
        }

        Entity entity = new Entity(name,parse,nbtList,isRealEntity,null,null);
        return entity;
    }
}
