package kr.kro.hurdoo.je1165.type.rjtf;

import com.google.gson.JsonObject;

public class HoverEvent {
    enum HoverActionType {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY,
        NONE;
    }

    private HoverActionType action = HoverActionType.NONE;
    private String contents = ""; // VALUE IN ~1.15

    public HoverActionType getAction() {
        return action;
    }
    public void setAction(HoverActionType action) {
        this.action = action;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }

    public JsonObject toJSON() {
        JsonObject object = new JsonObject();
        if(action == HoverActionType.NONE) return object;

        if(action == HoverActionType.SHOW_TEXT) object.addProperty("action","show_text");
        if(action == HoverActionType.SHOW_ITEM) object.addProperty("action","show_item");
        if(action == HoverActionType.SHOW_ENTITY) object.addProperty("action","show_entity");

        object.addProperty("contents",contents);

        return object;
    }

    public HoverEvent clone() {
        HoverEvent event = new HoverEvent();
        event.setAction(action);
        event.setContents(contents);
        return event;
    }
}
