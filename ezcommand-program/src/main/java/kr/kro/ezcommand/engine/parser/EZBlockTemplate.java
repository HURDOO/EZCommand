package kr.kro.ezcommand.engine.parser;

import java.util.HashMap;

public interface EZBlockTemplate extends EZElementContainer {

    /**
     * 이 블록의 요소들을 반환하는 함수입니다.
     *
     * A function that returns the Block's elements.
     *
     * @return HashMap of EZBlockElement
     */
    HashMap<String, EZBlockElement> getElements();

    // @TODO: is getElements() really needed?

    /**
     * 이 블록의 커맨드 구문을 반환하는 함수입니다.
     * 맨 앞의 슬래쉬(/)는 제외하여 반환해주세요.
     *
     * A function that returns this EZBlock as Minecraft Command.
     * Slash(/) is not required.
     *
     * @return Minecraft Command of EZBlock
     */
    String toCommand();
}
