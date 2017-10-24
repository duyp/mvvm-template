package com.duyp.architecture.mvvm.data.provider.timeline.handler;

import android.text.SpannableStringBuilder;

import com.duyp.architecture.mvvm.data.provider.emoji.Emoji;
import com.duyp.architecture.mvvm.data.provider.emoji.EmojiManager;
import com.duyp.architecture.mvvm.helper.Logger;

import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

/**
 * Created by Kosh on 27 May 2017, 4:54 PM
 */

public class EmojiHandler extends TagNodeHandler {

    @Override public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end) {
        String emoji = node.getAttributeByName("alias");
        if (emoji != null) {
            Emoji unicode = EmojiManager.getForAlias(emoji);
            if (unicode != null && unicode.getUnicode() != null) {
                builder.replace(start, end, " " + unicode.getUnicode() + " ");
            }
        } else if (node.getText() != null) {
            Logger.e(node.getText());
            Emoji unicode = EmojiManager.getForAlias(node.getText().toString());
            if (unicode != null && unicode.getUnicode() != null) {
                builder.replace(start, end, " " + unicode.getUnicode() + " ");
            }
        }
    }

    @Override public void beforeChildren(TagNode node, SpannableStringBuilder builder) {
        super.beforeChildren(node, builder);
    }
}
