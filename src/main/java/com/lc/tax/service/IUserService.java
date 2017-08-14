package com.lc.tax.service;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:22
 * @Modified By:
 */
import com.lc.tax.model.User;

public interface IUserService {

    User selectUser(long userId);

}