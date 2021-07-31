package com.dayshuai.bloguser.service;

import com.dayshuai.bloguser.dto.UUser;

/**
 * @ClassName : UserService
 * @Description : 用户接口
 * @Author : dayshuai
 * @Date: 2021-07-17 20:17
 */
public interface UserService {
    UUser getUserById(long id);

}
