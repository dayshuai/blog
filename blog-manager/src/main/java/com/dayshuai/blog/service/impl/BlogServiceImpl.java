package com.dayshuai.blog.service.impl;

import com.dayshuai.blog.dao.BBlogMapper;
import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : BlogServiceImpl
 * @Description : blogserviceImpl
 * @Author : dayshuai
 * @Date: 2021-07-29 13:52
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BBlogMapper bBlogMapper;
    @Override
    public BBlog getBlogById(long id) {
        return bBlogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertBlog(BBlog blog) {
        return bBlogMapper.insertSelective(blog);
    }

    @Override
    public List<BBlog> queryBlogList(BBlog bBlog) {
        return bBlogMapper.selectBlogList(bBlog);
    }

    @Override
    public int deleteBlog(Long id) {
        return bBlogMapper.deleteByPrimaryKey(id);
    }


}
