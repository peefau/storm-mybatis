package com.lc.tax.dao;

import com.lc.tax.model.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:21
 * @Modified By:
 */
@Repository("userDao")
public interface IUserDao {

    User selectUser(long id);

}