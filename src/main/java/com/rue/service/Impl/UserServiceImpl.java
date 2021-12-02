package com.rue.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rue.bean.User;
import com.rue.mapper.UserMapper;
import com.rue.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ruetrash
 */
@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
}
