package com.lc.tax.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.DRPCClient;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident.testing.Split;


public class WordCountTopology {

    public static void main(String[] args) throws Exception {
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"));
        spout.setCycle(false);

        TridentTopology topology = new TridentTopology();
        TridentState wordCounts =
                topology.newStream("spout1", spout)
                        .each(new Fields("sentence"), new Split(), new Fields("word"))
                        .groupBy(new Fields("word"))
                        .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"))
                        .parallelismHint(6);

        Config cf = new Config();
        cf.setNumWorkers(2);
        cf.setNumAckers(1);
        cf.setDebug(false);

//        DRPCClient client = new DRPCClient("pingfudeMacBook-Pro.local", 3772);
//        System.out.println(client.execute("words", "cat dog the man"));
//
//        topology.newDRPCStream("words")
//                .each(new Fields("args"), new Split(), new Fields("word"))
//                .groupBy(new Fields("word"))
//                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
//                .each(new Fields("count"), new FilterNull())
//                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

        LocalCluster lc = new LocalCluster();
        lc.submitTopology("TestTopology", cf, topology.build());

        Thread.sleep(3000);

        lc.shutdown();
    }
}