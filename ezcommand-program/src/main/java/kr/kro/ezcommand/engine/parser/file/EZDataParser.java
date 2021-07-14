package kr.kro.ezcommand.engine.parser.file;

import com.google.gson.JsonObject;

public interface EZDataParser {
    EZData parse(JsonObject object);
}
