package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.TextLabel;
import org.json.simple.JSONObject;

public class EZBlockParser {
    public static kr.kro.ezcommand.engine.parser.EZBlock parse(JSONObject object)
    {
        String description = object.get("description").toString();
        String parse = object.get("parse").toString();
        kr.kro.ezcommand.engine.parser.EZBlock ezBlock = new EZBlock(description,parse); // new object

        char[] content = object.get("content").toString().toCharArray();
        JSONObject argList = (JSONObject) object.get("args");
        String str = "";
        boolean entered = false;
        Integer textCnt = 0;

        for(int i=0;i<content.length;i++)
        {
            if(content[i] != '%')
                str += content[i];
            else
            {
                if(entered == false) {
                    /*
                        arg 시작
                        이미 있는 str은 TextLabel로
                     */
                    TextLabel label = new TextLabel(ezBlock,textCnt,str);
                    ezBlock.addElement(label);
                    textCnt++;

                    entered = true;
                    str = "";
                }
                else {
                    /*
                        arg 끝
                        이미 있는 str을 EZBlockElement로
                     */
                    JSONObject arg = (JSONObject) argList.get(str);
                    EZBlockElement element = EZBlockElementParser.parse(ezBlock,str,arg);
                    ezBlock.addElement(element);

                    entered = false;
                    str = "";
                }
            }
        }
        if(str != "") {
            // 맨 마지막이 TextLabel이어서 %로 끝나지 않았을 경우
            TextLabel label = new TextLabel(ezBlock,textCnt,str);
            ezBlock.addElement(label);
            textCnt++;
        }
        return ezBlock;
    }
}
