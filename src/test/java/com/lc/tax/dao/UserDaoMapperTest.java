package com.lc.tax.dao;

/*
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午11:36
 * @Modified By:
 */
import com.lc.tax.dao.hx_zs.ZsJksMapper;
import com.lc.tax.pojo.hx_zs.ZsJks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


// 加载spring配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class UserDaoMapperTest {

    @Autowired
    private ZsJksMapper zsJksMapper;

    @Test
    public void testSelectUser() throws Exception {
        String spuuid = "D5B1191DC63C119B632B3FAAA0B988B0";
        ZsJks zsJks = zsJksMapper.selectByPrimaryKey(spuuid);
        System.out.println("record: "+zsJks.getZsuuid()+","+zsJks.getCkzhzhuuid());
    }
}