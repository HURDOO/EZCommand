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
import javafx.util.Pair;
import kr.kro.ezcommand.Main;
import kr.kro.ezcommand.engine.EZTab;
import kr.kro.ezcommand.ui.BlockList;
import kr.kro.ezcommand.ui.stage.MainStage;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EZBlock implements kr.kro.ezcommand.engine.parser.type.EZBlock {

    public EZBlock(String description, String parse) {

        this.description = description;
        this.parse = parse;

        /* 기본 UI 설정*/
        ui = new HBox();
        ui.setAlignment(Pos.CENTER);

        ui.getStylesheets().add("/src/main/resources/css/Font.css");
        ui.getStylesheets().add("/css/Font.css");

        path = new Path();
        root.getChildren().add(path);
        root.getChildren().add(ui);
        ui.setLayoutX(contentDistanceX);
        ui.setLayoutY(contentDistanceY);


        /* 마우스 드래그 설정 */
        EZBlockUtil.setMovable(this);
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
    private Path path;
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

    boolean isExampleBlock = false;

    public void setExampleBlock(boolean bool) {
        isExampleBlock = bool;
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

    private EZBlock parent;
    public EZBlock getParent() {
        return parent;
    }
    public void setParent(EZBlock block) {
        if(block == null) {
            setParentOnly(null);
            return;
        }

        /*
            1
            <- 4 5
            2
            3

            1.setChildren(4)
            4.setParent(oldest(2))

            youngest(4).setChildren(2)
            2.setParent(youngest(4))
         */

        if(parent != null) {
            parent.setChildrenOnly(block);
            block.setParentOnly(parent);
        }
        //EZBlockUtil.getYoungest(block).setChildren(this);
        EZBlock youngest = EZBlockUtil.getYoungest(block);
        youngest.setChildrenOnly(this);
        setParentOnly(youngest);
    }
    public void setParentOnly(EZBlock block) { // caution
        parent = block;
    }

    private EZBlock children;
    public EZBlock getChildren() {
        return children;
    }
    public void setChildrenOnly(EZBlock block) {
        children = block;
    }
    public void setChildren(EZBlock block) { // external
        if(block == null) {
            setChildrenOnly(null);
            return;
        }
        if(children != null) {
            children.setParent(block);
            return;
        }

        if(children != null)
        {
            EZBlock youngest = EZBlockUtil.getYoungest(block);
            youngest.setChildren(children);
            children.setParentOnly(youngest);
        }
        children = block;
        block.setParentOnly(this);
    }

    public static final double pathDistanceX = 5; // 0 ~ path의 꼭짓점을 향해 내려가기 시작하기까지의 거리
    public static final double pathDistanceY = 3; // 0 ~ path의 윗쪽 꼭짓점의 가장 낮은 높이
    public static final double contentDistanceX = 5; // anchorpane ~ hbox
    public static final double contentDistanceY = 5; // anchorpane ~ hbox
    public void resize() {
        ui.autosize();
        Platform.runLater(() -> {
            Path path = (Path) root.getChildren().get(0);
            path.getElements().clear();
            double maxx = ui.getWidth(),maxy = ui.getHeight();
            path.getElements().add(new MoveTo(0,0)); //0 0
            path.getElements().add(new LineTo(pathDistanceX,0)); // 5 0
            path.getElements().add(new LineTo(pathDistanceX*2,pathDistanceY)); // 10 3
            path.getElements().add(new LineTo(pathDistanceX*3,0)); // 15 0
            path.getElements().add(new LineTo(maxx+contentDistanceX*2,0)); // max+10 0
            path.getElements().add(new LineTo(maxx+contentDistanceX*2,maxy+contentDistanceY*2)); // max+10 max+10
            path.getElements().add(new LineTo(pathDistanceX*3,maxy+10)); // 15 max+10
            path.getElements().add(new LineTo(pathDistanceX*2,maxy+13)); // 10 max+13
            path.getElements().add(new LineTo(5,maxy+10)); // 5 max+10
            path.getElements().add(new LineTo(0,maxy+10)); // 0 max+10
            path.getElements().add(new LineTo(0,0)); // 0 0
        });
    }

    public void rearrangeFromHere() {
        rearrange(root.getLayoutX(),root.getLayoutY());
    }
    private void rearrange(double x,double y) {
        //ui.setBackground(null);
        resize();
        root.setLayoutX(x);
        root.setLayoutY(y);

        if(children != null)
            children.rearrange(root.getLayoutX(),root.getLayoutY() + ui.getHeight() + contentDistanceY * 2);
        /*else
            ui.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));*/
    }

    public EZBlock clone() throws CloneNotSupportedException {

        EZBlock block = new EZBlock(description,parse);
        for(String id : elementList) {
            block.addElement(elements.get(id).clone(block));
        }
        return block;
    }
}

class EZBlockUtil {
    /*
     * 최초 클릭 시:
     *  창 좌표 = 블록의 스크린 좌표 - 블록의 창 좌표
     *  차이 = 마우스의 스크린 좌표 - 블록의 스크린 좌표
     *
     * 드래그 때마다:
     *  마우스의 창 좌표 = 마우스의 스크린 좌표 - 창 좌표 - 차이
     */

    private static double window_x,window_y,diff_x,diff_y;
    private static Pair<EZBlock,Boolean> nextBlock; // true = block is parent / false = block is children
    public static void setMovable(EZBlock block) {
        Pane ui = block.getUi();

        ui.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    if(block.isExampleBlock) {
                        EZBlock clone;
                        try {
                            clone = block.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                            return;
                        }

                        ui.setLayoutX(ui.getLocalToSceneTransform().getTx());
                        ui.setLayoutY(ui.getLocalToSceneTransform().getTy());

                        int index = BlockList.getUi().getChildren().indexOf(ui);
                        BlockList.getUi().getChildren().remove(index);
                        BlockList.getUi().getChildren().add(index,clone.getUi());
                        MainStage.backPane.getChildren().add(block.getUi());

                        block.setExampleBlock(false);
                        clone.setExampleBlock(true);

                        Platform.runLater(() -> {
                            block.resize();
                            clone.resize();
                        });
                    }
                    else
                    {
                        EZTab.nowTab.removeBlock(block);
                        if(block.getParent() != null) {
                            block.getParent().setChildren(null);
                            block.setParent(null);
                        }

                        ui.setLayoutX(ui.getLocalToSceneTransform().getTx());
                        ui.setLayoutY(ui.getLocalToSceneTransform().getTy());

                        for(EZBlock block1 : getAllChildren(block))
                        {
                            EZTab.nowTab.getUiPane().getChildren().remove(block1.getUi());
                            MainStage.backPane.getChildren().add(block1.getUi());
                        }
                        //MainStage.backPane.getChildren().add(ui);
                        block.rearrangeFromHere();
                    }

                    event.consume();
                    ui.setMouseTransparent(true);
                    event.setDragDetect(true);

                    saveCurrentMouse(ui);

                    ui.requestFocus();
                }
            }
        });

        ui.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    ui.setMouseTransparent(false);

                    Bounds uiBounds = ui.localToScene(ui.getLayoutBounds());
                    Bounds paneBounds = EZTab.nowTab.getUiPane().localToScene(EZTab.nowTab.getUiPane().getLayoutBounds());
                    Bounds paneScreenBounds = EZTab.nowTab.getUiPane().localToScreen(EZTab.nowTab.getUiPane().getLayoutBounds());
                    Point mouse = MouseInfo.getPointerInfo().getLocation();
                    //MainStage.backPane.getChildren().remove(ui);
                    for(EZBlock block1 : getAllChildren(block))
                    {
                        MainStage.backPane.getChildren().remove(block1.getUi());
                    }

                    if(mouse.getX() >= paneScreenBounds.getMinX()) {
                        ui.setLayoutX(uiBounds.getMinX() - paneBounds.getMinX());
                        ui.setLayoutY(uiBounds.getMinY() - paneBounds.getMinY());

                        for(EZBlock block1 : getAllChildren(block))
                        {
                            EZTab.nowTab.getUiPane().getChildren().add(block1.getUi());
                        }

                        if(nextBlock == null) {
                            EZTab.nowTab.addBlock(block);
                            getOldest(block).rearrangeFromHere();
                        } else {
                            if(nextBlock.getValue() == true) // if nextBlock is parent
                            {
                                nextBlock.getKey().setChildren(block);
                                getOldest(nextBlock.getKey()).rearrangeFromHere();
                            }
                            else // if nextblock is children
                            {
                                nextBlock.getKey().setParent(block);
                                if(nextBlock.getKey().getParent() != null)
                                    EZTab.nowTab.changeBlock(nextBlock.getKey(), getOldest(block));
                                getOldest(block).rearrangeFromHere();
                            }
                        }
                    }
                    if(nextBlock != null) {
                        nextBlock.getKey().getUi().setBorder(null);
                    }
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
                    block.rearrangeFromHere();
                    event.setDragDetect(false);

                    if(nextBlock != null) nextBlock.getKey().getUi().setBorder(null);

                    nextBlock = findNearestBlock(block); // true = return is parent / false = return is children
                    if(nextBlock != null) {
                        if(nextBlock.getValue() == true) // if nextBlock is parent
                        {
                            nextBlock.getKey().getUi().setBorder(new Border(new BorderStroke(Color.RED, Color.RED, Color.RED, Color.RED,
                                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                                null, new BorderWidths(2), null)));
                        }
                        else // if nextBlock is children
                        {
                            nextBlock.getKey().getUi().setBorder(new Border(new BorderStroke(Color.BLUE, Color.RED, Color.RED, Color.RED,
                                    BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                                    null, new BorderWidths(2), null)));
                        }
                    }

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

    private static void saveCurrentMouse(Pane ui) {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Bounds bounds = ui.localToScreen(ui.getBoundsInLocal());
        window_x = bounds.getMinX() - ui.getLayoutX();
        window_y = bounds.getMinY() - ui.getLayoutY();
        diff_x = mouse.getX() - bounds.getMinX();
        diff_y = mouse.getY() - bounds.getMinY();
    }


    /*
     * distX = minX ~ 꼭짓점 시작하기까지의 X
     * distY = minY ~ 위쪽 꼭짓점까지의 Y
     *
     * source's [minX,minY] 가 target's [minX-distX,maxY-(height/2)+1] ~ [minX+(3*distX),maxY+height] 안에 있음
     * source's [minX,maxY-distY] 가 target's [minX-distX,minY-height] ~ [minX+(3*distX),minY+(height/2)-1] 안에 있음
     */

    private static Pair<EZBlock,Boolean> findNearestBlock(EZBlock block) {

        Bounds source = block.getUi().localToScene(block.getUi().getLayoutBounds());
        double distX = EZBlock.pathDistanceX;
        double distY = EZBlock.pathDistanceY;

        List<EZBlock> list = new ArrayList<>();
        for(EZBlock nextBlock : EZTab.nowTab.getBlocks()) {
            list.addAll(Arrays.asList(getAllChildren(nextBlock)));
        }
        for (EZBlock nextBlock : list) {
            Bounds target = nextBlock.getUi().localToScene(nextBlock.getUi().getLayoutBounds());

            double downSourceX = source.getMinX();
            double downSourceY = source.getMinY();
            double downTargetX1 = target.getMinX() - distX;
            double downTargetY1 = target.getMaxY() - (target.getHeight()/2) + 1;
            double downTargetX2 = target.getMinX() + (3 * distX);
            double downTargetY2 = target.getMaxY() + target.getHeight();

            if(downTargetX1 <= downSourceX && downSourceX <= downTargetX2
                && downTargetY1 <= downSourceY && downSourceY <= downTargetY2)
            {
                return new Pair<>(nextBlock,true); // nextBlock is parent
            }

            // source's [minX,maxY-distY] 가 target's [minX-distX,minY-height] ~ [minX+(3*distX),minY+(height/2)-1] 안에 있음

            double upSourceX = source.getMinX();
            double upSourceY = source.getMaxY() - distY;
            double upTargetX1 = target.getMinX() - distX;
            double upTargetY1 = target.getMinY() - target.getHeight();
            double upTargetX2 = target.getMinX() + (3 * distX);
            double upTargetY2 = target.getMinY() + (target.getHeight()/2) - 1;

            if(upTargetX1 <= upSourceX && upSourceX <= upTargetX2
                && upTargetY1 <= upSourceY && upSourceY <= upTargetY2)
            {
                return new Pair<>(nextBlock,false); // nextBlock is children
            }
        }
        return null;
    }

    public static EZBlock getYoungest(EZBlock block) {
        if(block.getChildren() == null) return block;
        return getYoungest(block.getChildren());
    }

    public static EZBlock getOldest(EZBlock block) {
        if(block.getParent() == null) return block;
        return getOldest(block.getParent());
    }

    public static EZBlock[] getAllChildren(EZBlock block) {
        List<EZBlock> blocks = new ArrayList<>();

        if(block.getChildren() != null)
            blocks.addAll(Arrays.asList(getAllChildren(block.getChildren())));
        blocks.add(0,block);

        return blocks.toArray(new EZBlock[0]);
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