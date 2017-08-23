package com.lc.tax.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.lc.tax.spout.FilterTest;
import com.lc.tax.spout.FunctionTest;
import com.lc.tax.spout.PrintResults;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;


public class TestTridentTopology {

    public static void main(String[] args) throws Exception {
//        ApplicationContext context = new
//                ClassPathXmlApplicationContext(new String[] {"classpath:spring-*.xml"});
//        UserServiceImpl userService = (UserServiceImpl)context.getBean("userService");
//
//        List<User> userList= userService.selectAllUser();
//        for(User user:userList)
//        {
//            System.out.println("records:"+user.getRegTime()+"<-->"+user.getUsername());
//        }

        FixedBatchSpout spout = new FixedBatchSpout(new Fields("name","idSex","tel"),
                3, new Values("Jack","1","186107"), new Values("Tome","2","1514697"), new Values(
                "Lay","3","186745"), new Values("Lucy","4","1396478"),new Values("Jerry","5","1768898"));

        FixedBatchSpout spout2 = new FixedBatchSpout(new Fields("sex","idSex"),
                3, new Values("Boy","1"), new Values("Boy","2"), new Values(
                "Girl","3"), new Values("Girl","4"),new Values("Boy","5"));


        //设置是否循环
        spout.setCycle(true);
        //构建Trident的Topology
        TridentTopology topology = new TridentTopology();
        //定义过滤器：电话号码不是186开头的过滤
        FilterTest ft =new FilterTest();
        //定义方法 用来显示过滤后的数据
        FunctionTest function = new FunctionTest();
        //根据spout构建第一个Stream
        Stream st = topology.newStream("sp1", spout);
        //对第一个Stream数据显示。
        st.each(new Fields("name","idSex","tel"), function, new Fields("out_name","out_idSex","out_tel"));
        //根据第二个Spout构建Stream，为了测试join用
        Stream st2 = topology.newStream("sp2", spout2);
        /*
         * 开始Join  st和st2这两个流，类比sql中join的话是：
         * st join st2 on  joinFields1 = joinFields2
         * 需要注意的是以st为数据基础
         * topology.join(st, new Fields("idSex"), st2, new Fields("idSex"), new Fields("id","name","tel","sex"))
         * 那么结果将是以spout为数据基础，结果会将上面的4个数据信息全部打出
         */
        Stream st3= topology.join(st, new Fields("idSex"), st2, new Fields("idSex"), new Fields("Res_id","Res_name","Res_tel","Res_sex"));
        //创建一个方法，为了显示合并和过滤后的结果
        PrintResults t3 = new PrintResults();
        st3.each(new Fields("Res_id","Res_name","Res_tel","Res_sex"), ft)
                .each(new Fields("Res_id","Res_name","Res_tel","Res_sex"),t3, new Fields("out_id","out_name","out_tel","out_sex"));

        Config cf = new Config();
        cf.setNumWorkers(2);
        cf.setNumAckers(2);
        cf.setDebug(false);

        LocalCluster lc = new LocalCluster();
        lc.submitTopology("TestTopology", cf, topology.build());

        Thread.sleep(6000);
        lc.killTopology("TestTopology");
        lc.shutdown();
    }
}