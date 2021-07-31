package com.dayshuai.bloguser.dao;

import com.dayshuai.bloguser.dto.URoleMenu;

public interface URoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(URoleMenu record);

    int insertSelective(URoleMenu record);

    URoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(URoleMenu record);

    int updateByPrimaryKey(URoleMenu record);
}