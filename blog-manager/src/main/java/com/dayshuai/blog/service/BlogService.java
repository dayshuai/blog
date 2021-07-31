package com.dayshuai.blog.service;

import com.dayshuai.blog.dto.BBlog;

import java.util.List;


public interface BlogService {
    BBlog getBlogById(long id);


    void insertBlog(BBlog blog);


    List<BBlog> queryBlogList(BBlog blog);

}
