package com.lc.tax.util;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.Split;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TridentStream2HbaseTopology {
    private static void tridentStreamToHbase(TridentTopology topology,FixedBatchSpout spout) {
        MyHbaseState.Options options = new MyHbaseState.Options();
        options.setTableName("storm_trident_state");
        options.setColumFamily("column1");
        options.setQualifier("q1");
        /*
         * 根据数据源拆分单词后,然后分区操作,在每个分区上又进行分组(hash算法),然后在每个分组上进行聚合
         * 所以这里可能有多个分区,每个分区有多个分组,然后在多个分组上进行聚合
         * 用来进行group的字段会以key的形式存在于State当中，聚合后的结果会以value的形式存储在State当中
         */
        TridentState tridentState = topology.newStream("sentencestream", spout)
                .each(new Fields("sentence"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .persistentAggregate(new MyHbaseState.HbaseFactory(options), new Count(), new Fields("count"))
                .parallelismHint(3);
    }

    public static void main(String[] args) throws Exception {
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("tan jie is a good man"),
                new Values("what is your name"),
                new Values("how old are you"),
                new Values("my name is tan jie"),
                new Values("i am 18 fadfa"));
        TridentTopology topology = new TridentTopology();
        spout.setCycle(true);
        tridentStreamToHbase(topology, spout);
        Config config = new Config();
        config.setDebug(false);
//        StormSubmitter.submitTopologyWithProgressBar("word_count_trident_state_HbaseState", config, topology.build());
        config.setNumWorkers(2);
        config.setNumAckers(2);
        LocalCluster lc = new LocalCluster();
        lc.submitTopology("TestTopology", config, topology.build());
        Thread.sleep(10000);
        lc.shutdown();
    }
}
