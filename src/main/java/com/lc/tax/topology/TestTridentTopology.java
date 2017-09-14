package com.lc.tax.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.lc.tax.pojo.hx_zs.ZsJks;
import com.lc.tax.serviceImpl.ZsJksMapperServiceImpl;
import com.lc.tax.spout.FilterTest;
import com.lc.tax.spout.ShowResult;
import com.lc.tax.spout.PrintResults;
import com.lc.tax.trident.MyCount;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import storm.trident.Stream;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;


public class TestTridentTopology {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new
                ClassPathXmlApplicationContext(new String[] {"classpath:spring-*.xml"});
        ZsJksMapperServiceImpl zsJKsMapperService = (ZsJksMapperServiceImpl)context.getBean("zsJKsMapperService");

        String spuuid = "41522DE6EA35E8384ECC766EED073884";
        ZsJks zsJks= zsJKsMapperService.selectByPrimaryKey(spuuid);
        System.out.println("records:"+zsJks.getZsuuid()+"<-->"+zsJks.getSpuuid());
//        for(ZsJks zsJks:zsJksList)
//        {
//            System.out.println("records:"+zsJks.getZsuuid()+"<-->"+zsJks.getSpuuid());
//        }

        FixedBatchSpout spout = new FixedBatchSpout(new Fields("name","idSex","tel"),
                6, new Values("Jack1","1","1861071"),new Values("Jack2","1","1861072"),
                new Values("Jack3","1","1861073"), new Values("Tome","2","1514697"),
                new Values("Lay","3","186745"), new Values("Lucy","4","1396478"),
                new Values("Jerry","5","1768898"), new Values("left-name1","6","1868898"));

        FixedBatchSpout spout2 = new FixedBatchSpout(new Fields("sex","idSex"),
                6, new Values("Boy","1"), new Values("Boy","2"), new Values(
                "Girl","3"), new Values("Girl","4"),new Values("Boy","5"));


        //设置是否循环
        spout.setCycle(true);
        spout2.setCycle(true);
        //构建Trident的Topology
        TridentTopology topology = new TridentTopology();
        //根据spout构建第一个Stream
        Stream st1 = topology.newStream("sp1", spout);
        //定义方法 用来显示过滤后的数据
        ShowResult showResult = new ShowResult();
        //对第一个Stream数据显示。
        st1.each(new Fields("name","idSex","tel"), showResult, new Fields("out_name","out_idSex","out_tel"));
        //根据第二个Spout构建Stream，为了测试join用
        Stream st2 = topology.newStream("sp2", spout2);
        /*
         * 开始Join  st和st2这两个流，类比sql中join的话是：
         * st join st2 on  joinFields1 = joinFields2
         * 需要注意的是以st为数据基础
         * topology.join(st, new Fields("idSex"), st2, new Fields("idSex"), new Fields("id","name","tel","sex"))
         * 那么结果将是以spout为数据基础，结果会将上面的4个数据信息全部打出
         */
        Stream st3= topology.join(st1, new Fields("idSex"), st2, new Fields("idSex"), new Fields("Res_id","Res_name","Res_tel","Res_sex"));
        //创建一个方法，为了显示合并和过滤后的结果
        PrintResults printResults = new PrintResults();
        //定义过滤器：电话号码不是186开头的过滤
        FilterTest filterTest =new FilterTest();
        TridentState personCount = st3.each(new Fields("Res_id","Res_name","Res_tel","Res_sex"), filterTest)
                .each(new Fields("Res_id","Res_name","Res_tel","Res_sex"),printResults, new Fields("out_id","out_name","out_tel","out_sex"))
                .groupBy(new Fields("out_id","out_name"))
                .persistentAggregate(new MemoryMapState.Factory(), new MyCount(), new Fields("count"))
                .parallelismHint(16);

        Config cf = new Config();
        cf.setNumWorkers(2);
        cf.setNumAckers(2);
        cf.setDebug(false);

        LocalCluster lc = new LocalCluster();
        lc.submitTopology("TestTopology", cf, topology.build());

        Thread.sleep(3000);
        lc.killTopology("TestTopology");
        lc.shutdown();
    }
}