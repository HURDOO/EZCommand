package kr.kro.ezcommand.old.inner;

import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class EZProject
{
    public static String ProjectName;
    public static double ProjectVersion;
    //public static HashMap<String, List<EZBlock>> mcfunction = new HashMap<>();
    public static List<EZTab> ezTabs = new ArrayList<EZTab>();
    public static HashMap<Tab,EZTab> getEZTab = new HashMap<>();
    public static MCEdition Edition;

    public static AnchorPane BlockListGUI = null;

    public enum MCEdition {
        JAVA("je", "자바 에디션"),
        BEDROCK("kr", "베드락 에디션");

        private final String code;
        private final String kr;

        private MCEdition(String code, String kr) {
            this.code = code;
            this.kr = kr;
        }

        public String toStringCode() {
            return this.code;
        }

        public String toString() {
            return this.kr;
        }
    }
}
