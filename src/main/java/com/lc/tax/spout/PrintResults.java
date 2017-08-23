package com.lc.tax.spout;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class PrintResults extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        System.out.println(tuple.getValues());
    }
}