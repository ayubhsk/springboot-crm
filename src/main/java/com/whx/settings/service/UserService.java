package com.whx.settings.service;

import com.whx.settings.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    User login(User loginUser);

    List<User> getAll();
}
