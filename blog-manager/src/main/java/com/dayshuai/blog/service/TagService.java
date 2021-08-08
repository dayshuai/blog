package com.dayshuai.blog.service;

import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.dto.Tag;

import java.util.List;


public interface TagService {
    Tag getTagById(long id);


    int insertTag(Tag blog);


    List<Tag> queryTagList(Tag tag);

    int deleteTag(Long id);


}
