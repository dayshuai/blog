package com.dayshuai.bloguser.service;

import com.dayshuai.bloguser.dto.UUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

/**
 * @ClassName : UserService
 * @Description : 用户接口
 * @Author : dayshuai
 * @Date: 2021-07-17 20:17
 */
public interface UserService {
    UUser getUserById(long id);

    void register(UUser user, String mailCode, String inviteCode);

    Map<String, Object> login(UUser user);

    UserDetails loadUserByUsername(String name) throws UsernameNotFoundException;

    void logOut();
}
