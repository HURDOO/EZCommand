package kr.kro.ezcommand.engine.parser;

import kr.kro.ezcommand.engine.parser.type.*;
import kr.kro.ezcommand.engine.parser.type.Number;
import kr.kro.ezcommand.engine.parser.type.rjtf.RawJsonTextFormat;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileParser
{
    private static JSONParser parser = new JSONParser();

    public static EZBlock parse(InputStream stream) throws IOException, ParseException {
        JSONObject object = (JSONObject) parser.parse(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String type = object.get("type").toString();
        switch(type)
        {
            case "block": // EZBlock
                return parseEZBlock(object);
            default: // nothing
                throw new IllegalArgumentException("Illegal type!");
        }
    }

    public static EZBlock parseEZBlock(JSONObject object)
    {
        String description = object.get("description").toString();
        String parse = object.get("parse").toString();
        EZBlock ezBlock = new EZBlock(description,parse); // new object

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
                    String type = arg.get("type").toString();
                    switch(type) {
                        case "number":
                            Number number = new Number(ezBlock,str,arg);
                            ezBlock.addElement(number);
                            break;
                        case "select":
                            Select select = new Select(ezBlock,str,arg);
                            ezBlock.addElement(select);
                            break;
                        case "raw_json_text_format":
                            RawJsonTextFormat rawJsonTextFormat = null;
                            try {
                                rawJsonTextFormat = new RawJsonTextFormat(ezBlock,str,arg);
                                ezBlock.addElement(rawJsonTextFormat);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "string":
                            StringLabel string = new StringLabel(ezBlock,str,arg);
                            ezBlock.addElement(string);
                            break;
                        case "boolean":
                            BooleanLabel bool = new BooleanLabel(ezBlock,str,arg);
                            ezBlock.addElement(bool);
                            break;
                        default:
                            throw new IllegalArgumentException("Illegal block type!");
                    }

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
