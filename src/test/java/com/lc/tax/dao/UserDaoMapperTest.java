package com.lc.tax.dao;

/*
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午11:36
 * @Modified By:
 */
import com.lc.tax.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

// 加载spring配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class UserDaoMapperTest {

    @Autowired
    private UserDaoMapper userDao;


    public void testSelectUser() throws Exception {
        long id = 1;
        User user = userDao.selectUser(id);
        System.out.println("record: "+user.getUsername()+","+user.getRegTime());
    }
    @Test
    public void testSelectALLUser() throws  Exception {
        List<User> userList= userDao.selectAllUser();
        for(User user:userList)
        {
            System.out.println("records:"+user.getRegTime()+"<-->"+user.getUsername());
        }
    }

    public void testInsertUser() throws Exception{
        User user = new User();
        user.setId(10L);
        user.setEmail("suxiwang@inspur.com");
        user.setPassword("qeqewrqwrqw");
        user.setUsername("suxiwang");
        user.setRole("employee");
        user.setStatus(1);
        user.setRegTime(new Date());
        user.setRegIp("192.168.1.189");

        userDao.insertUser(user);
    }

    public void testDeleteUser() throws Exception {
        long id = 10L;
        userDao.deleteUserById(id);
        this.testSelectALLUser();
    }

    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setId(6L);
        user.setEmail("suxiwang@inspur.com");
        user.setPassword("rrrrrrrrrrrrrrr");
        user.setUsername("suxiwang");
        user.setRole("employee");
        user.setStatus(1);
        user.setRegTime(new Date());
        user.setRegIp("192.168.1.189");

        userDao.updateUserById(user);
        System.out.println("update success!");
    }
}