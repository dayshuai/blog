package com.dayshuai.blog.service;

import com.dayshuai.blog.dto.BBlog;

import java.util.List;


public interface BlogService {
    BBlog getBlogById(long id);


    void insertBlog(BBlog blog, Integer[] tagIds);


    List<BBlog> queryBlogList(BBlog blog);

    int deleteBlog(Long id);



    void updateBlog(Long blogId, String title, String content, Integer[] tagIds);
}
