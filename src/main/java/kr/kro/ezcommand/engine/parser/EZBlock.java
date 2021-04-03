package kr.kro.ezcommand.engine.parser;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EZBlock extends Object {

    public EZBlock(String description, String parse) {

        this.parse = parse;

        /* 기본 UI 설정*/
        ui = new HBox();
        ui.setAlignment(Pos.CENTER);
        setBackgroundColor(Color.TRANSPARENT);

        ui.getStylesheets().add("/src/main/resources/css/EZBlock.css");
        ui.getStylesheets().add("/css/EZBlock.css");

        ui.getStyleClass().add("ezblock");

        /* 마우스 드래그 설정 */
        MousePoint.setMovable(ui);

        setBackgroundColor(Color.AQUA); //for test, remove later
    }

    /**
     * 요소들을 가로로 나열해야 하기에 기본 Pane으로 Horizontal Box 사용.
     */

    private HBox ui;
    public HBox getUi() {
        return ui;
    }

    /**
     * 요소(Element)는 총 2곳에 저장
     *  - elementList (내부 해쉬맵 / UI를 포함한 요소의 모든 정보 저장)
     *  - ui.getChildren() (HBox / UI만 저장)
     */

    private HashMap<String,EZBlockElement> elements = new HashMap<>();
    public HashMap<String, EZBlockElement> getElements() {
        return elements;
    }

    public void addElement(EZBlockElement element) {
        /* 요소 저장 */
        elements.put(element.getId(),element);

        /* UI 저장 */
        ui.getChildren().add(element.getUI());
    }

    /**
     * HBox의 배경색
     */
    public Color getBackgroundColor() {
        return (Color) ui.getBackground().getFills().get(0).getFill();
    }
    public void setBackgroundColor(Color bgColor) {
        ui.setBackground(new Background(new BackgroundFill(bgColor,null,null)));
    }

    public void setPinned() {
        MousePoint.setPinned(ui);
    }

    private final String parse;
    public String toCommand() {
        StringBuilder command = new StringBuilder();

        char[] ch = parse.toCharArray();
        String str = "";
        boolean entered = false; // if arg input started

        for(char c : ch)
        {
            if(c != '%')
            {
                str += c;
            }
            else if(entered == false)
            {
                /* normal text */
                command.append(str);

                str = "";
                entered = true;
            }
            else
            {
                /* arg */
                command.append(elements.get(str).toCommand());

                str = "";
                entered = false;
            }
        }
        if(str != "")
        {
            // if str doesn't end with '%'
            command.append(str);
        }
        return command.toString();
    }

}

class MousePoint {
    /*
     * 최초 클릭 시:
     *  창 좌표 = 블록의 스크린 좌표 - 블록의 창 좌표
     *  차이 = 마우스의 스크린 좌표 - 블록의 스크린 좌표
     *
     * 드래그 때마다:
     *  마우스의 창 좌표 = 마우스의 스크린 좌표 - 창 좌표 - 차이
     */

    static double window_x,window_y,diff_x,diff_y;
    public static void setMovable(HBox ui) {
        ui.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    event.consume();
                    ui.setMouseTransparent(true);
                    event.setDragDetect(true);

                    Point mouse = MouseInfo.getPointerInfo().getLocation();
                    Bounds bounds = ui.localToScreen(ui.getBoundsInLocal());
                    window_x = bounds.getMinX() - ui.getLayoutX();
                    window_y = bounds.getMinY() - ui.getLayoutY();
                    diff_x = mouse.getX() - bounds.getMinX();
                    diff_y = mouse.getY() - bounds.getMinY();

                    //System.out.printf("mouse:%f, bounds: %f, ui: %f\n", mouse.getX(), bounds.getMinX(), ui.getLayoutX());
                }
            }
        });

        ui.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    ui.setMouseTransparent(false);
                }
            }
        });

        ui.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    Point mouse = MouseInfo.getPointerInfo().getLocation();
                    ui.setLayoutX(mouse.getX() - window_x - diff_x);
                    ui.setLayoutY(mouse.getY() - window_y - diff_y);
                    event.setDragDetect(false);

                   // System.out.printf("mouse:%f, ui: %f\n", mouse.getX(), ui.getLayoutX());
                }
            }
        });

        ui.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    ui.startFullDrag();
                }
            }
        });
    }
    public static void setPinned(HBox ui)
    {
        ui.setOnMousePressed(null);
        ui.setOnMouseReleased(null);
        ui.setOnMouseDragged(null);
        ui.setOnDragDetected(null);
    }
}

/*public EZBlock(String url) throws IOException, ParseException {
        String path = "src/main/resources" + url;
        ui = FileParser.parse(new File(path));
        //ui.getBackground().getFills().add(new BackgroundFill(Color.TRANSPARENT,null,null));
        ui.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        ui.getStylesheets().add("/src/main/resources/css/EZBlock.css");
        ui.getStyleClass().add("ezblock");
        /*ui.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
    }*/