package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;
import kr.kro.ezcommand.engine.parser.type.BooleanLabel;
import kr.kro.ezcommand.engine.parser.type.Number;
import kr.kro.ezcommand.engine.parser.type.Select;
import kr.kro.ezcommand.engine.parser.type.StringLabel;
import kr.kro.ezcommand.engine.parser.type.rjtf.RawJsonTextFormat;
import org.json.simple.JSONObject;

import java.io.IOException;

public class EZBlockElementParser {
    public static EZBlockElement parse(EZElementContainer parent, String str, JSONObject arg) {
        String type = arg.get("type").toString();
        switch(type) {
            case "number":
                Number number = new Number(parent,str,arg);
                return number;

            case "select":
                Select select = new Select(parent,str,arg);
                return select;

            case "raw_json_text_format":
                RawJsonTextFormat rawJsonTextFormat = new RawJsonTextFormat(parent,str,arg);
                return rawJsonTextFormat;
            case "string":
                StringLabel string = new StringLabel(parent,str,arg);
                return string;

            case "boolean":
                BooleanLabel bool = new BooleanLabel(parent,str,arg);
                return bool;

            default:
                throw new IllegalArgumentException("Illegal block type!");
        }
    }
}
