package com.lc.tax.serviceImpl;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:22
 * @Modified By:
 */
import com.lc.tax.dao.UserDaoMapper;
import com.lc.tax.model.User;
import com.lc.tax.service.IUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDaoMapper userDao;

    @Override
    public User selectUser(long userId) {
        return userDao.selectUser(userId);
    }
    @Override
    public List<User> selectAllUser(){
        return  userDao.selectAllUser();
    }
    @Override
    public boolean insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public boolean deleteUserById(long id) {
        return userDao.deleteUserById(id);
    }

    @Override
    public boolean updateUserById(User user) {
        return userDao.updateUserById(user);
    }
}