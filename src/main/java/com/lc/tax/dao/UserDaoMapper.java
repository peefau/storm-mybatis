package com.lc.tax.dao;

import com.lc.tax.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:21
 * @Modified By:
 */
@Repository("userDao")
public interface UserDaoMapper {

    User selectUser(long id);

    List<User> selectAllUser();

    boolean insertUser(User user);

    boolean deleteUserById(long id);

    boolean updateUserById(User user);
}