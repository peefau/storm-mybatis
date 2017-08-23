package com.lc.tax.spout;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class FunctionTest extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String getName = tuple.getString(0);
        String getid = tuple.getString(1);
        String getTel = tuple.getString(2);
        System.out.println("过滤后数据：============"+tuple.getValues());
        collector.emit(new Values(getName,getid,getTel));
    }

}
