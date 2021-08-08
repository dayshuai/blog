package com.dayshuai.blog.dao;

import com.dayshuai.blog.dto.BBlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BBlogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BBlog record);

    int insertSelective(BBlog record);

    List<BBlog> selectBlogList(BBlog record);

    BBlog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BBlog record);

    int updateByPrimaryKeyWithBLOBs(BBlog record);

    int updateByPrimaryKey(BBlog record);

    int saveBlogTag(@Param("blogId")Long blogId, @Param("tagId")int tagId);

    int deleteTagsById(Long blogId);
}