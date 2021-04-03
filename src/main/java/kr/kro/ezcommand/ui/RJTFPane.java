package kr.kro.ezcommand.ui;

import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.TextOps;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class RJTFPane extends GenericStyledArea {

    public RJTFPane(Object initialParagraphStyle, BiConsumer applyParagraphStyle, Object initialTextStyle, TextOps segmentOps, Function nodeFactory) {
        super(initialParagraphStyle, applyParagraphStyle, initialTextStyle, segmentOps, nodeFactory);
    }


}
