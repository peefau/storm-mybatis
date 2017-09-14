package com.lc.tax.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.lc.tax.pojo.hx_zs.ZsJks;
import com.lc.tax.serviceImpl.ZsJksMapperServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing Transactional topologies in Trident. Borrowed heavily from the WordCount example in Storm
 *
 */
public class TransactionalWordCount {
    public static class Split extends BaseFunction {
        @Override
        public void execute(TridentTuple tuple, TridentCollector collector) {
            String sentence = tuple.getString(0);
            for (String word : sentence.split(" ")) {
                collector.emit(new Values(word));
            }
        }
    }
    private static TransactionalFixedBatchSpout makeTransactionalSpout() {
        //数据来源：取自mysql数据库，将数据放入list变量data中
        ApplicationContext context = new
                ClassPathXmlApplicationContext(new String[] {"classpath:spring-*.xml"});
        ZsJksMapperServiceImpl zsJKsMapperService = (ZsJksMapperServiceImpl)context.getBean("zsJKsMapperService");

        String spuuid = "41522DE6EA35E8384ECC766EED073884";
        ZsJks zsJks= zsJKsMapperService.selectByPrimaryKey(spuuid);
        System.out.println("records:"+zsJks.getZsuuid()+"<-->"+zsJks.getSpuuid());

        List<List<Values>> data = new ArrayList<List<Values>>();
        List<Values> block = new ArrayList<Values>();
        block.add(new Values(zsJks.getSpuuid(),zsJks.getZsuuid(),zsJks.getCkzhzhuuid()));
//        for(User user:userList)
//        {
//            System.out.println("records:"+user.getRegTime()+"<-->"+user.getUsername());
//            block.add(new Values(user.getId(),user.getRole(),user.getPassword()));
//        }
        data.add(block);

        //判断被处理数据的长度，根据data.size()来计算spout并行度
        int parallelism = 1;

        if(data.size() >= 3){
            parallelism = data.size();
        }

        return new TransactionalFixedBatchSpout(new Fields("code", "source", "sentence"), parallelism, data);
    }

    private static StormTopology buildPPTopology() {
        //创建流式拓扑图
        TransactionalFixedBatchSpout spout = makeTransactionalSpout();
        TridentTopology topology = new TridentTopology();

        TridentState ppState;
        ppState = topology.newStream("PPSpout990", spout)
                .parallelismHint(16)
                .each(new Fields("sentence"), new Split(), new Fields("word"))
                .shuffle()
                .groupBy(new Fields("source", "word"))
                .aggregate(new Fields("code", "source", "word"), new MyCount(), new Fields("counts"))
                .partitionPersist(new SumDBFactory(), new Fields("source", "word", "counts"), new SumUpdater())
                .parallelismHint(16);
        return topology.build();
    }
    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        if (args.length == 0) {
            conf.setNumWorkers(2);
            conf.setNumAckers(2);
//            LocalDRPC drpc = new LocalDRPC();
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordCounter", conf, buildPPTopology());
            Thread.sleep(6000);
            cluster.killTopology("wordCounter");
            cluster.shutdown();
        } else {
            conf.setNumWorkers(5);
            conf.setNumAckers(5);
            StormSubmitter.submitTopology(args[0], conf, buildPPTopology());
        }
        System.exit(0);
    }
}

