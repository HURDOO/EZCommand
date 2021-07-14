package kr.kro.hurdoo.je1165.type.rjtf;

import com.google.gson.JsonObject;

public class ClickEvent {
    enum ClickActionType {
        OPEN_URL,
        @Deprecated OPEN_FILE, // DISABLED
        RUN_COMMAND,
        CHANGE_PAGE,
        SUGGEST_COMMAND,
        COPY_TO_CLIPBOARD, // FROM 1.15~
        NONE
    }

    private ClickActionType action = ClickActionType.NONE;
    private String value = "";

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public ClickActionType getAction() {
        return action;
    }
    public void setAction(ClickActionType action) {
        this.action = action;
    }


    public JsonObject toJSON() {
        JsonObject object = new JsonObject();
        if(action == ClickActionType.NONE) return object;
        if(action == ClickActionType.OPEN_FILE) throw new NullPointerException("clickEvent.action: OPEN_FILE is unavailable in command.");

        if(action == ClickActionType.OPEN_URL) object.addProperty("action","open_url");
        if(action == ClickActionType.RUN_COMMAND) object.addProperty("action","run_command");
        if(action == ClickActionType.CHANGE_PAGE) object.addProperty("action","change_page");
        if(action == ClickActionType.SUGGEST_COMMAND) object.addProperty("action","suggest_command");
        if(action == ClickActionType.COPY_TO_CLIPBOARD) object.addProperty("action","copy_to_clipboard");

        object.addProperty("value",value);
        return object;
    }

    public ClickEvent clone() {
        ClickEvent event = new ClickEvent();
        event.setAction(action);
        event.setValue(value);
        return event;
    }
}
