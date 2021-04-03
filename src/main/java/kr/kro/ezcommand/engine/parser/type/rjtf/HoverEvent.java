package kr.kro.ezcommand.engine.parser.type.rjtf;

import org.json.simple.JSONObject;

public class HoverEvent {
    enum HoverActionType {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY,
        NONE;
    }

    private HoverActionType action = HoverActionType.NONE;
    private String contents = ""; // VALUE IN ~1.15

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        if(action == HoverActionType.NONE) return object;

        if(action == HoverActionType.SHOW_TEXT) object.put("action","show_text");
        if(action == HoverActionType.SHOW_ITEM) object.put("action","show_item");
        if(action == HoverActionType.SHOW_ENTITY) object.put("action","show_entity");

        object.put("contents",contents);

        return object;
    }
}
