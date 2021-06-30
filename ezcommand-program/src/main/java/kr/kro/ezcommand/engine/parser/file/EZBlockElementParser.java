package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.EZCommand;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class EZBlockElementParser {
    public static EZBlockElement parse(EZElementContainer parent, String str, JSONObject arg) {
        assert arg.get("type") != null;
        String type = arg.get("type").toString();
        Class<? extends EZBlockElement> elementClass = EZCommand.EZBlockElements.get(type).asSubclass(EZBlockElement.class);

        EZBlockElement element;
        try {
            element = elementClass
                    .getConstructor(EZElementContainer.class,String.class,JSONObject.class)
                    .newInstance(parent,str,arg);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
        return element;
    }
}

/*
import kr.kro.hurdoo.je1165.type.BooleanLabel;
import kr.kro.hurdoo.je1165.type.Number;
import kr.kro.hurdoo.je1165.type.Select;
import kr.kro.hurdoo.je1165.type.StringLabel;
import kr.kro.hurdoo.je1165.type.rjtf.RawJsonTextFormat;

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
*/