package com.lc.tax.spout;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class FilterTest  extends BaseFilter {

    @Override
    public boolean isKeep(TridentTuple tuple) {
        String get =tuple.getString(2);
        return get.startsWith("186");
    }
}