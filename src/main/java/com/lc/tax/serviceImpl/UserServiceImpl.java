package com.lc.tax.serviceImpl;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:22
 * @Modified By:
 */
import com.lc.tax.dao.IUserDao;
import com.lc.tax.model.User;
import com.lc.tax.service.IUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    public User selectUser(long userId) {
        return this.userDao.selectUser(userId);
    }

}