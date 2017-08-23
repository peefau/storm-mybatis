package com.lc.tax.service;

/*
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:22
 * @Modified By:
 */
import com.lc.tax.model.User;

import java.util.List;

public interface IUserService {

    User selectUser(long userId);

    List<User> selectAllUser();

    boolean insertUser(User user);

    boolean deleteUserById(long id);

    boolean updateUserById(User user);
}