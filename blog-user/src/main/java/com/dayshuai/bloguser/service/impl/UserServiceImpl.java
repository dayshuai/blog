package com.dayshuai.bloguser.service.impl;

import com.dayshuai.bloguser.dao.URoleMapper;
import com.dayshuai.bloguser.dao.UUserMapper;
import com.dayshuai.bloguser.dao.UUserRoleMapper;
import com.dayshuai.bloguser.dto.URole;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.bloguser.dto.UUserRole;
import com.dayshuai.bloguser.service.UserService;
import com.dayshuai.common.config.JwtConfig;
import com.dayshuai.common.enums.RoleType;
import com.dayshuai.common.utils.JwtTokenUtil;
import com.dayshuai.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtConfig jwtConfig;


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

    @Override
    public Map<String, Object> login(UUser user) {
        UUser dbUser = userMapper.findUserByName(user.getUserName());
        //此用户不存在 或 密码错误
        if (null == dbUser || !StringUtils.equals(user.getPassword(), dbUser.getPassword())) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }


        //用户名 密码 匹配 签发token
        final UserDetails userDetails = this.loadUserByUsername(user.getUserName());

        final String token = jwtTokenUtil.generateToken(userDetails);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        Map<String, Object> map = new HashMap<>(3);

        map.put("token", jwtConfig.getPrefix() + token);
        map.put("name", user.getUserName());
        map.put("roles", roles);

        /*//将token存入redis 过期时间 jwtConfig.time 单位[s]
        redisTemplate.opsForValue().
                set(JwtConfig.REDIS_TOKEN_KEY_PREFIX + user.getName(), jwtConfig.getPrefix() + token, jwtConfig.getTime(), TimeUnit.SECONDS);
        */
        return map;
    }

    /**
     * 根据用户名查询用户
     *
     * @param name
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UUser user = userMapper.findUserByName(name);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(1);
        //用于添加用户的权限。将用户权限添加到authorities
        List<URole> roles = roleMapper.findUserRoles(user.getId());
        for (URole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), "***********", authorities);
    }
}


