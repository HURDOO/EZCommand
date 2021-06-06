package kr.kro.hurdoo.je1165.type.rjtf;

import com.cathive.fonts.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import kr.kro.hurdoo.je1165.type.rjtf.JsonText;
import kr.kro.hurdoo.je1165.type.rjtf.RawJsonTextFormat;
import org.fxmisc.richtext.InlineCssTextArea;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class RJTFTextEditorController implements Initializable
{
    @FXML Button bold;
    @FXML Button italic;
    @FXML Button underlined;
    @FXML Button strikethrough;
    @FXML Button obfuscated;
    @FXML ColorPicker color;
    @FXML Button clickEvent;
    @FXML Button hoverEvent;

    @FXML InlineCssTextArea area;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateTexts();
        area.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));

        bold.setOnAction(event -> {
            int start = area.getSelection().getStart(), end = area.getSelection().getEnd();
            String isBold = getStyleValue(area.getStyleOfChar(start),"-fx-font-weight",true);

            assert isBold != null;
            if(isBold.equalsIgnoreCase("bold")) {
                replaceStyle(start,end,"-fx-font-weight","normal");
                ((FontAwesomeIconView) bold.getGraphic()).setTextFill(Color.BLACK);
            }
            else if(isBold.equalsIgnoreCase("normal")) {
                replaceStyle(start,end,"-fx-font-weight","bold");
                ((FontAwesomeIconView) bold.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            }
            else {
                throw new IllegalArgumentException("isBold is: " + isBold);
            }
            area.requestFocus();
        });

        italic.setOnAction(event -> {
            int start = area.getSelection().getStart(), end = area.getSelection().getEnd();
            String isItalic = getStyleValue(area.getStyleOfChar(start),"-fx-font-family",true)
                    .replace("\"","");

            if(isItalic.equalsIgnoreCase("NanumBarunGothic_Italic")) {
                replaceStyle(start,end,"-fx-font-family","NanumBarunGothic");
                ((FontAwesomeIconView) italic.getGraphic()).setTextFill(Color.BLACK);
            }
            else if(isItalic.equalsIgnoreCase("NanumBarunGothic")) {
                replaceStyle(start,end,"-fx-font-family","NanumBarunGothic_Italic");
                ((FontAwesomeIconView) italic.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            }
            else {
                throw new IllegalArgumentException("isItalic is: " + isItalic);
            }
            area.requestFocus();
        });

        underlined.setOnAction(event -> {
            int start = area.getSelection().getStart(), end = area.getSelection().getEnd();
            String isUnderlined = getStyleValue(area.getStyleOfChar(start),"-fx-underline",true);

            assert isUnderlined != null;
            if(isUnderlined.equalsIgnoreCase("true")) {
                replaceStyle(start,end,"-fx-underline","false");
                ((FontAwesomeIconView) underlined.getGraphic()).setTextFill(Color.BLACK);
            }
            else if(isUnderlined.equalsIgnoreCase("false")) {
                replaceStyle(start,end,"-fx-underline","true");
                ((FontAwesomeIconView) underlined.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            }
            else {
                throw new IllegalArgumentException("isUnderlined is: " + isUnderlined);
            }
            area.requestFocus();
        });

        strikethrough.setOnAction(event -> {
            int start = area.getSelection().getStart(), end = area.getSelection().getEnd();
            String isStrikethrough = getStyleValue(area.getStyleOfChar(start),"-fx-strikethrough",true);

            assert isStrikethrough != null;
            if(isStrikethrough.equalsIgnoreCase("true")) {
                replaceStyle(start,end,"-fx-strikethrough","false");
                ((FontAwesomeIconView) strikethrough.getGraphic()).setTextFill(Color.BLACK);
            }
            else if(isStrikethrough.equalsIgnoreCase("false")) {
                replaceStyle(start,end,"-fx-strikethrough","true");
                ((FontAwesomeIconView) strikethrough.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            }
            else {
                throw new IllegalArgumentException("isStrikethrough is: " + isStrikethrough);
            }
            area.requestFocus();
        });

        obfuscated.setOnAction(event -> {
            int start = area.getSelection().getStart(), end = area.getSelection().getEnd();
            String isObfuscated = getStyleValue(area.getStyleOfChar(start),"-rtfx-background-color",true);

            assert isObfuscated != null;
            if(isObfuscated.equalsIgnoreCase("#bababa")) {
                replaceStyle(start,end,"-rtfx-background-color","none");
                ((FontAwesomeIconView) obfuscated.getGraphic()).setTextFill(Color.BLACK);
            }
            else if(isObfuscated.equalsIgnoreCase("none")) {
                replaceStyle(start,end,"-rtfx-background-color","#bababa");
                ((FontAwesomeIconView) obfuscated.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            }
            else {
                throw new IllegalArgumentException("isObfuscated is: " + isObfuscated);
            }
            area.requestFocus();
        });

        color.setOnAction(event -> {
            int start = area.getSelection().getStart(), end = area.getSelection().getEnd();

            replaceStyle(start,end,"-fx-fill",toHexString(color.getValue()));
        });

        area.selectionProperty().addListener((observable, oldValue, newValue) -> {
            int start = newValue.getStart();

            String str = getStyleValue(area.getStyleOfChar(start),"-fx-font-weight",true);
            assert str != null;
            if(str.equalsIgnoreCase("bold"))
                ((FontAwesomeIconView) bold.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            else if(str.equalsIgnoreCase("normal"))
                ((FontAwesomeIconView) bold.getGraphic()).setTextFill(Color.BLACK);

            str = getStyleValue(area.getStyleOfChar(start),"-fx-font-family",true)
                .replace("\"","");
            if(str.equalsIgnoreCase("NanumBarunGothic_Italic"))
                ((FontAwesomeIconView) italic.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            else if(str.equalsIgnoreCase("NanumBarunGothic"))
                ((FontAwesomeIconView) italic.getGraphic()).setTextFill(Color.BLACK);

            str = getStyleValue(area.getStyleOfChar(start),"-fx-underline",true);
            assert str != null;
            if(str.equalsIgnoreCase("true"))
                ((FontAwesomeIconView) underlined.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            else if(str.equalsIgnoreCase("false"))
                ((FontAwesomeIconView) underlined.getGraphic()).setTextFill(Color.BLACK);

            str = getStyleValue(area.getStyleOfChar(start),"-fx-strikethrough",true);
            assert str != null;
            if(str.equalsIgnoreCase("true"))
                ((FontAwesomeIconView) strikethrough.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            else if(str.equalsIgnoreCase("false"))
                ((FontAwesomeIconView) strikethrough.getGraphic()).setTextFill(Color.BLACK);

            str = getStyleValue(area.getStyleOfChar(start),"-rtfx-background-color",true);
            assert str != null;
            if(str.equalsIgnoreCase("#bababa"))
                ((FontAwesomeIconView) obfuscated.getGraphic()).setTextFill(Color.SKYBLUE.darker());
            else if(str.equalsIgnoreCase("none"))
                ((FontAwesomeIconView) obfuscated.getGraphic()).setTextFill(Color.BLACK);

            str = getStyleValue(area.getStyleOfChar(start),"-fx-fill",true);
            assert str != null;
            color.setValue(Color.valueOf(str));
        });
    }

    public RawJsonTextFormat getNow() {
        return now;
    }
    RawJsonTextFormat now = null;

    public void register(RawJsonTextFormat rjtf)
    {
        now = rjtf;
        updateTexts();
    }

    public void updateTexts()
    {
        if(now == null) return;
        area.deleteText(0,area.getText().length());

        area.setStyle("-fx-fill: white; -fx-font-weight: normal; -fx-font-family: \"NanumBarunGothic\"; " +
                "-fx-underline:false; -fx-strikethrough: false; -rtfx-background-color: none;");

        for(JsonText text : now.getContent()) {

            StringBuilder style = new StringBuilder();

            if(text.getColor() == Color.TRANSPARENT)
                style.append("-fx-fill: white; ");
            else
                style.append("-fx-fill: " + toHexString(text.getColor()) + "; ");

            if(text.isBold())
                style.append("-fx-font-weight: bold; ");
            else
                style.append("-fx-font-weight: normal; ");

            if(text.isItalic())
                style.append("-fx-font-family: \"NanumBarunGothic_Italic\"; ");
            else
                style.append("-fx-font-family: \"NanumBarunGothic\"; ");

            if(text.isUnderlined())
                style.append("-fx-underline: true; ");
            else
                style.append("-fx-underline: false; ");

            if(text.isStrikethrough())
                style.append("-fx-strikethrough: true; ");
            else
                style.append("-fx-strikethrough: false; ");

            if(text.isObfuscated())
                style.append("-rtfx-background-color: #bababa; ");
            else
                style.append("-rtfx-background-color: none; ");

            area.append(text.getText(), style.toString());
        }
    }

    public void save() {
        now.getContent().clear();
        now.getContent().addAll(toJson());
    };

    public List<JsonText> toJson() {
        List<JsonText> texts = new LinkedList<>();
        for(int i=0;i<=area.getLength();i++) {
            int start = area.getStyleRangeAtPosition(i).getStart(), end = area.getStyleRangeAtPosition(i).getEnd();
            String style = area.getStyleOfChar(start);
            String str;
            JsonText text = new JsonText();

            text.setText(area.getText(start,end));

            str = getStyleValue(style,"-fx-fill",true);
            text.setColor(Color.valueOf(str));

            str = getStyleValue(style,"-fx-font-weight",true);
            if(str.equalsIgnoreCase("bold")) text.setBold(true);
            else if(str.equalsIgnoreCase("normal")) text.setBold(false);
            else throw new IllegalArgumentException("bold is: " + str);

            str = getStyleValue(style,"-fx-font-family",true).replace("\"","");
            if(str.equalsIgnoreCase("NanumBarunGothic_Italic")) text.setItalic(true);
            else if (str.equalsIgnoreCase("NanumBarunGothic")) text.setItalic(false);
            else throw new IllegalArgumentException("italic is: " + str);

            str = getStyleValue(style,"-fx-underline",true);
            if(str.equalsIgnoreCase("true")) text.setUnderlined(true);
            else if (str.equalsIgnoreCase("false")) text.setUnderlined(false);
            else throw new IllegalArgumentException("underlined is: " + str);

            str = getStyleValue(style,"-fx-strikethrough",true);
            if(str.equalsIgnoreCase("true")) text.setStrikethrough(true);
            else if (str.equalsIgnoreCase("false")) text.setStrikethrough(false);
            else throw new IllegalArgumentException("strikethrough is: " + str);

            str = getStyleValue(style,"-rtfx-background-color",true);
            if(str.equalsIgnoreCase("#bababa")) text.setObfuscated(true);
            else if (str.equalsIgnoreCase("none")) text.setObfuscated(false);
            else throw new IllegalArgumentException("background for obfuscated is: " + str);

            texts.add(text);
            i = end;
        }
        return texts;
    }

    private String format(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    private String toHexString(Color value) {
        return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
                .toUpperCase();
    }

    private String getStyleValue(String style,String key,boolean withoutSpaces) {
        String[] splited = style.split(";");
        for(String str : splited) {
            String blankRemoved = str.replace(" ","").replace("\0","");
            if(blankRemoved.startsWith(key))
            {
                if(withoutSpaces) {
                    return str.replaceFirst(key,"").replace(":","")
                            .replace(" ","").replace("\0","");
                }
                return str.replaceFirst(key,"").replace(':','\0');
            }
        }
        return null;
    }

    private void replaceStyle(int start,int end,String key,String value) {
        for(int i=start;i<end;i++)
        {
            String origin = area.getStyleOfChar(i);

            if(getStyleValue(origin,key,true).equalsIgnoreCase(value)) continue;

            String[] splitedone = origin.split(key);
            if(splitedone.length != 2) throw new IllegalArgumentException("style is: " + origin);
            String[] splitedtwo = splitedone[1].split(";",2);
            String changed = splitedone[0] + key + ": " + value + ";" +splitedtwo[1];
            area.setStyle(i,i+1,changed);
        }
    }
}