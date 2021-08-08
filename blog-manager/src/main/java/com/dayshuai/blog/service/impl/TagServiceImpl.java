package com.dayshuai.blog.service.impl;

import com.dayshuai.blog.dao.TagMapper;
import com.dayshuai.blog.dto.Tag;
import com.dayshuai.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public Tag getTagById(long id) {
        return null;
    }

    @Override
    public int insertTag(Tag blog) {
        return 0;
    }

    @Override
    public List<Tag> queryTagList(Tag tag) {
        return tagMapper.queryTagList(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return 0;
    }
}
