package kr.kro.ezcommand.engine.parser;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import kr.kro.ezcommand.ui.BlockList;
import kr.kro.ezcommand.ui.stage.MainStage;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EZBlock extends Object {

    public EZBlock(String description, String parse) {

        this.description = description;
        this.parse = parse;

        /* 기본 UI 설정*/
        ui = new HBox();
        ui.setAlignment(Pos.CENTER);
        setBackgroundColor(Color.TRANSPARENT);

        ui.getStylesheets().add("/src/main/resources/css/EZBlock.css");
        ui.getStylesheets().add("/css/EZBlock.css");
        ui.getStylesheets().add("/src/main/resources/css/Font.css");
        ui.getStylesheets().add("/css/Font.css");
        //ui.getStyleClass().add("ezblock");

        Path newPath = new Path();
        newPath.setFill(Color.AQUA);
        root.getChildren().add(newPath);
        root.getChildren().add(ui);
        ui.setLayoutX(5);
        ui.setLayoutY(5);


        /* 마우스 드래그 설정 */
        MousePoint.setMovable(root);
        Platform.runLater(() -> {
            this.resize();
        });

        //setBackgroundColor(Color.AQUA); //for test, remove later
    }

    private String description;

    /**
     * 요소들을 가로로 나열해야 하기에 기본 Pane으로 Horizontal Box 사용.
     */

    private HBox ui;
    private AnchorPane root = new AnchorPane();
    public AnchorPane getUi() {
        return root;
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
    private List<String> elementList = new LinkedList<>();

    public void addElement(EZBlockElement element) {
        /* 요소 저장 */
        elements.put(element.getId(),element);
        elementList.add(element.getId());

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

    public void setAsExampleBlock() {
        MousePoint.setCloneable(this);
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

    public void resize() {
        ui.autosize();
        Platform.runLater(() -> {
            Path path = (Path) root.getChildren().get(0);
            path.getElements().clear();
            double maxx = ui.getWidth(),maxy = ui.getHeight();
            path.getElements().add(new MoveTo(0,0));
            path.getElements().add(new LineTo(5,0));
            path.getElements().add(new LineTo(10,3));
            path.getElements().add(new LineTo(15,0));
            path.getElements().add(new LineTo(maxx+10,0));
            path.getElements().add(new LineTo(maxx+10,maxy+10));
            path.getElements().add(new LineTo(15,maxy+10));
            path.getElements().add(new LineTo(10,maxy+13));
            path.getElements().add(new LineTo(5,maxy+10));
            path.getElements().add(new LineTo(0,maxy+10));
            path.getElements().add(new LineTo(0,0));
        });
    }

    public EZBlock clone() throws CloneNotSupportedException {

        EZBlock block = new EZBlock(description,parse);
        for(String id : elementList) {
            block.addElement(elements.get(id).clone(block));
        }
        block.setBackgroundColor(getBackgroundColor());

        return block;
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
    public static void setMovable(Pane ui) {
        ui.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    event.consume();
                    ui.setMouseTransparent(true);
                    event.setDragDetect(true);

                    saveCurrentMouse(ui);

                    ui.requestFocus();

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
    public static void setCloneable(EZBlock block) {
        Pane ui = block.getUi();
        ui.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    // clone

                    EZBlock clone;
                    try {
                        clone = block.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return;
                    }
                    BlockList.getUi().getChildren().set(BlockList.getUi().getChildren().indexOf(ui),clone.getUi());
                    MainStage.backPane.getChildren().add(block.getUi());

                    setMovable(block.getUi());
                    setCloneable(clone);

                    Platform.runLater(() -> {
                        block.resize();
                        clone.resize();
                    });


                    //move

                    event.consume();
                    ui.setMouseTransparent(true);
                    event.setDragDetect(true);

                    saveCurrentMouse(ui);

                    ui.requestFocus();

                }
            }
        });
    }
    private static void saveCurrentMouse(Pane ui) {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Bounds bounds = ui.localToScreen(ui.getBoundsInLocal());
        window_x = bounds.getMinX() - ui.getLayoutX();
        window_y = bounds.getMinY() - ui.getLayoutY();
        diff_x = mouse.getX() - bounds.getMinX();
        diff_y = mouse.getY() - bounds.getMinY();
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