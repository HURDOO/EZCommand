package kr.kro.ezcommand.engine.parser.ezc;

import com.google.gson.JsonObject;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.TextLabel;

public class EZBlockParser {
    public static kr.kro.ezcommand.engine.parser.EZBlock parse(JsonObject object)
    {
        String description = object.get("description").getAsString();
        String parse = object.get("parse").getAsString();
        kr.kro.ezcommand.engine.parser.EZBlock ezBlock = new EZBlock(description,parse); // new object

        char[] content = object.get("content").getAsString().toCharArray();
        JsonObject argList = object.get("args").getAsJsonObject();
        String str = "";
        boolean entered = false;
        int textCnt = 0;

        for (char c : content) {
            if (c != '%')
                str += c;
            else {
                if (!entered) {
                    /*
                        arg 시작
                        이미 있는 str은 TextLabel로
                     */
                    TextLabel label = new TextLabel(textCnt, str);
                    ezBlock.addElement(label);
                    textCnt++;

                    entered = true;
                } else {
                    /*
                        arg 끝
                        이미 있는 str을 EZBlockElement로
                     */
                    JsonObject arg = argList.get(str).getAsJsonObject();
                    EZBlockElement element = EZBlockElementParser.parse(str, arg);
                    ezBlock.addElement(element);

                    entered = false;
                }
                str = "";
            }
        }
        if(!str.equals("")) {
            // 맨 마지막이 TextLabel이어서 %로 끝나지 않았을 경우
            TextLabel label = new TextLabel(textCnt,str);
            ezBlock.addElement(label);
        }
        return ezBlock;
    }
}
