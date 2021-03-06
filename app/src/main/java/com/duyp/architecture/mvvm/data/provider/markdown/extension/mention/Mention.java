package com.duyp.architecture.mvvm.data.provider.markdown.extension.mention;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Delimited;

/**
 * Created by kosh on 20/08/2017.
 */

public class Mention extends CustomNode implements Delimited {

    private static final String DELIMITER = "@";

    @Override public String getOpeningDelimiter() {
        return DELIMITER;
    }

    @Override public String getClosingDelimiter() {
        return " ";
    }
}
