package com.dayshuai.bloguser.service.impl;

import com.dayshuai.bloguser.dao.UUserMapper;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.bloguser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : UserServiceImpl
 * @Description : 用户接口实现
 * @Author : dayshuai
 * @Date: 2021-07-17 20:21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UUserMapper userMapper;
    @Override
    public UUser getUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
