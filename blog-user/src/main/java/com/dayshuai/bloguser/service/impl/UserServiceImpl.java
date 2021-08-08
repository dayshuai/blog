package com.dayshuai.bloguser.service.impl;

import com.dayshuai.bloguser.dao.URoleMapper;
import com.dayshuai.bloguser.dao.UUserMapper;
import com.dayshuai.bloguser.dao.UUserRoleMapper;
import com.dayshuai.bloguser.dto.URole;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.bloguser.dto.UUserRole;
import com.dayshuai.bloguser.service.UserService;
import com.dayshuai.common.enums.RoleType;
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

    @Autowired
    URoleMapper roleMapper;

    @Autowired
    UUserRoleMapper userRoleMapper;


    @Override
    public UUser getUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public void register(UUser user, String mailCode, String inviteCode) {
        user.setUserType(RoleType.USER.getValue());
        userMapper.insertSelective(user);
        URole role = roleMapper.selectByRoleType(RoleType.USER.getValue());
        //用户角色关系表
        UUserRole userRole = new UUserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleMapper.insertSelective(userRole);
    }
}
