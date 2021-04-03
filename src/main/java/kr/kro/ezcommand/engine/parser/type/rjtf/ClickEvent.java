package kr.kro.ezcommand.engine.parser.type.rjtf;

import org.json.simple.JSONObject;

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

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        if(action == ClickActionType.NONE) return object;
        if(action == ClickActionType.OPEN_FILE) throw new NullPointerException("clickEvent.action: OPEN_FILE is unavailable in command.");

        if(action == ClickActionType.OPEN_URL) object.put("action","open_url");
        if(action == ClickActionType.RUN_COMMAND) object.put("action","run_command");
        if(action == ClickActionType.CHANGE_PAGE) object.put("action","change_page");
        if(action == ClickActionType.SUGGEST_COMMAND) object.put("action","suggest_command");
        if(action == ClickActionType.COPY_TO_CLIPBOARD) object.put("action","copy_to_clipboard");

        object.put("value",value);
        return object;
    }
}
