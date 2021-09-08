package kr.kro.hurdoo.je1165.type.rjtf;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class JsonText {
    private String text = "";

    private Color color = Color.TRANSPARENT;
    private boolean bold = false;
    private boolean italic = false;
    private boolean obfuscated = false;
    private boolean underlined = false;
    private boolean strikethrough = false;

    private ClickEvent clickEvent = null;
    private HoverEvent hoverEvent = null;

    private final List<JsonText> extra = new ArrayList<>();

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        if (color != null && color != Color.TRANSPARENT) {
            object.addProperty("color", parseHelper.colorToHex(color));
        }
        if (bold) {
            object.addProperty("bold", true);
        }
        if (italic) {
            object.addProperty("italic", true);
        }
        if (obfuscated) {
            object.addProperty("obfuscated", true);
        }
        if (underlined) {
            object.addProperty("underlined", true);
        }
        if (strikethrough) {
            object.addProperty("strikethrough", true);
        }
        try {
            object.add("clickEvent", clickEvent.toJSON());
        } catch (NullPointerException ignored) {
        }
        try {
            object.add("hoverEvent", hoverEvent.toJSON());
        } catch (NullPointerException ignored) {
        }
        if (!extra.isEmpty()) {
            JsonArray array = new JsonArray();
            for (JsonText object1 : extra) {
                if (object1.toJsonString().startsWith("\""))
                    array.add(object1.toJsonString()); // Single text
                else
                    array.add(object1.toJson());
            }
            object.add("extra", array);
        }
        //if(onlyText) return text;
        object.addProperty("text",text);
        return object;
    }

    public String toJsonString() {
        return toJson().toString();
    }

    private static class parseHelper {
        private static String format(double val) {
            String in = Integer.toHexString((int) Math.round(val * 255));
            return in.length() == 1 ? "0" + in : in;
        }

        private static String toHexString(Color value) {
            return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) /*+ format(value.getOpacity())*/)
                    .toUpperCase();
        }

        public static String colorToHex(Color color) {
            String hex = toHexString(color);
            switch (hex) {
                case "#AA0000":
                    return "dark_red";
                case "#FF5555":
                    return "red";
                case "#FFAA00":
                    return "gold";
                case "#FFFF55":
                    return "yellow";
                case "#00AA00":
                    return "dark_green";
                case "#55FF55":
                    return "green";
                case "#55FFFF":
                    return "aqua";
                case "#00AAAA":
                    return "dark_aqua";
                case "#0000AA":
                    return "dark_blue";
                case "5555FF":
                    return "blue";
                case "#FF55FF":
                    return "light_purple";
                case "#AA00AA":
                    return "dark_purple";
                case "#FFFFFF":
                    return "white";
                case "#AAAAAA":
                    return "gray";
                case "#555555":
                    return "dark_gray";
                case "#000000":
                    return "black";
                default:
                    return hex;
            }
        }
    }



    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public boolean isBold() {
        return bold;
    }
    public void setBold(boolean bold) {
        this.bold = bold;
    }
    public boolean isItalic() {
        return italic;
    }
    public void setItalic(boolean italic) {
        this.italic = italic;
    }
    public boolean isObfuscated() {
        return obfuscated;
    }
    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }
    public boolean isUnderlined() {
        return underlined;
    }
    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }
    public boolean isStrikethrough() {
        return strikethrough;
    }
    public void setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
    }
    public ClickEvent getClickEvent() {
        return clickEvent;
    }
    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }
    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }
    public void setHoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
    }
    public List<JsonText> getExtra() {
        return extra;
    }

    // @TODO: make extra
    /* public void setExtra(List<JsonText> extra) {
        this.extra = extra;
    } */


    public JsonText clone() {
        JsonText text = new JsonText();
        text.setText(this.text);
        text.setColor(color);
        text.setBold(bold);
        text.setItalic(italic);
        text.setUnderlined(underlined);
        text.setStrikethrough(strikethrough);
        text.setObfuscated(obfuscated);
        try {
            text.setClickEvent(clickEvent.clone());
        } catch (NullPointerException e) {
            text.setClickEvent(null);
        }
        try {
            text.setHoverEvent(hoverEvent.clone());
        } catch (NullPointerException e) {
            text.setHoverEvent(null);
        }
        for(JsonText extraText : extra) {
            text.getExtra().add(extraText.clone());
        }
        return text;
    }
}
