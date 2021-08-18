package com.dayshuai.blog.service;

import com.dayshuai.blog.dto.BBlog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface BlogService {
    BBlog getBlogById(long id);


    void insertBlog(BBlog blog, Integer[] tagIds);


    List<BBlog> queryBlogList(BBlog blog);

    int deleteBlog(Long id);



    void updateBlog(Long blogId, String title, String content, Integer[] tagIds);

    List<BBlog> queryBlogByUser();

    String saveImg(MultipartFile file) throws IOException;
}
