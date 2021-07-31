package com.dayshuai.bloguser.dao;

import com.dayshuai.bloguser.dto.UUserRole;

public interface UUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UUserRole record);

    int insertSelective(UUserRole record);

    UUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UUserRole record);

    int updateByPrimaryKey(UUserRole record);
}