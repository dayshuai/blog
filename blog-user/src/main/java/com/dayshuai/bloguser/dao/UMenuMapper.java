package com.dayshuai.bloguser.dao;

import com.dayshuai.bloguser.dto.UMenu;

public interface UMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UMenu record);

    int insertSelective(UMenu record);

    UMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UMenu record);

    int updateByPrimaryKey(UMenu record);
}