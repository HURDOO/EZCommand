package kr.kro.ezcommand.old.inner.EZBlocks;

import javafx.fxml.FXMLLoader;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.gui.b_main.blocks.weather.WEATHERController;
import kr.kro.ezcommand.old.inner.EZBlock;
import kr.kro.ezcommand.old.inner.EZBlockType;

import java.io.IOException;

import static kr.kro.ezcommand.old.inner.EZBlocks.WEATHER.WEATHERType.CLEAR;

public class WEATHER extends EZBlock {

    public WEATHERType weatherType;
    public int seconds;

    public WEATHER()
    {
        blockType = EZBlockType.WEATHER;
        if(BlockListGUIController.defaultWeather == null)
        {
            weatherType = CLEAR;
            seconds = 300;
            parent = null;
        }
        else
        {
            weatherType = BlockListGUIController.defaultWeather.weatherType;
            seconds = BlockListGUIController.defaultWeather.seconds;
            parent = BlockListGUIController.selectedBlock.parent;
        }

        BlockListGUIController.nowWeather = this;

        try {
            pane = FXMLLoader.load(WEATHERController.class.getResource("/kr/kro/ezcommand/gui/b_main/blocks/weather/WEATHER.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "weather " + weatherType.toString() + " " + seconds;
    }

    public enum WEATHERType {
        CLEAR ("clear","맑게"),
        RAIN ("rain","비가 오게"),
        THUNDER ("thunder","폭풍우가 몰아치게");

        private final String code;
        private final String kr;

        private WEATHERType(String code,String kr) {
            this.code = code;
            this.kr = kr;
        }

        public String toStringCode() {
            return this.code;
        }

        public String toStringKr() {
            return this.kr;
        }
    }

    /*public String weatherTypeToString(WEATHERType type)
    {
        switch(type)
        {
            case CLEAR:
                return "clear";
            case RAIN:
                return "rain";
            case THUNDER:
                return "thunder";
            default:
                throw new NullPointerException("Out of Enum \"WEATHERType\"");
        }
    }
     */
}

