package kr.kro.ezcommand.old.inner;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public abstract class EZBlock
{
    public EZBlockType blockType;
    public AnchorPane pane;
    public EZMcfunction parent;
    // public String idonthaveanyideatomakethis_andiwonderwhyihavetodothis_imtoosleepyiwanttogohome_sos_helpmeplease;
    // 2020.11.27 2학년 방송부 UCC공모전 준비 갈려나간 흔적... ㅠ

    public Button upButton;
    public Button downButton;

    public abstract String toString();
}
