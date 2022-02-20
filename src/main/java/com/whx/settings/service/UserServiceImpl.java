package com.whx.settings.service;


import com.whx.settings.dao.UserDao;
import com.whx.settings.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public User login(User loginUser) {
        User user=userDao.login(loginUser);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> userList=userDao.getAll();
        return userList;

    }
}
