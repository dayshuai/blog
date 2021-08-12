package com.dayshuai.bloguser.dao;

import com.dayshuai.bloguser.dto.URole;

import java.util.List;

public interface URoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);

    URole selectByRoleType(String roleType);

    List<URole> findUserRoles(Long id);
}