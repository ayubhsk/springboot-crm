package com.whx.settings.dao;


import com.whx.settings.domain.User;

import java.util.List;

public interface UserDao {

    User login(User loginUser);

    List<User> getAll();
}
