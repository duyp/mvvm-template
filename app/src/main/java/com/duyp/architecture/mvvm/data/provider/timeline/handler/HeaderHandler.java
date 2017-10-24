package com.duyp.architecture.mvvm.data.provider.timeline.handler;

import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;

import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.spans.FontFamilySpan;

import org.htmlcleaner.TagNode;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class HeaderHandler extends TagNodeHandler{

    private final float size;

    public HeaderHandler(float size) {
        this.size = size;
    }

    @Override
    public void beforeChildren(TagNode node, SpannableStringBuilder builder) {
        appendNewLine(builder);
    }

    @Override
    public void handleTagNode(TagNode tagNode, SpannableStringBuilder builder, int start, int end) {
        builder.setSpan(new RelativeSizeSpan(this.size), start, end, 33);
        FontFamilySpan originalSpan = this.getFontFamilySpan(builder, start, end);
        FontFamilySpan boldSpan;
        if (originalSpan == null) {
            boldSpan = new FontFamilySpan(getSpanner().getDefaultFont());
        } else {
            boldSpan = new FontFamilySpan(originalSpan.getFontFamily());
            boldSpan.setItalic(originalSpan.isItalic());
        }
        boldSpan.setBold(true);
        builder.setSpan(boldSpan, start, end, 33);
        appendNewLine(builder);
    }
}
