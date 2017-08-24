package com.lc.tax.trident;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.State;
import storm.trident.state.StateFactory;

import java.util.Map;

public class SumDBFactory implements StateFactory {

    public State makeState(Map a_conf, IMetricsContext a_context, int a_partitionIndex, int a_numPartitions) {
        return new SumDB();
    }
}

