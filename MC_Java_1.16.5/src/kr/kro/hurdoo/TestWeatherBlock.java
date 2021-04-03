package kr.kro.hurdoo;

import kr.kro.ezcommand.engine.parser.type.EZBlock;
import kr.kro.ezcommand.engine.parser.type.EZBlockElement;

import java.util.ArrayList;
import java.util.List;

public class TestWeatherBlock implements EZBlock {

    List<EZBlockElement> elements = new ArrayList<>();

    @Override
    public List<EZBlockElement> getElements() {
        return elements;
    }

    @Override
    public String toCommand() {
        return null;
    }
}
