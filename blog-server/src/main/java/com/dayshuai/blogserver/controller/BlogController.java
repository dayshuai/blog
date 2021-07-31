package com.dayshuai.blogserver.controller;

import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import com.dayshuai.bloguser.dao.URoleMapper;
import com.dayshuai.bloguser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @ClassName : UUserController
 * @Description : 用户前端controller
 * @Author : dayshuai
 * @Date: 2021-07-17 20:16
 */
@Controller
@RequestMapping(value = "/blog/blog")
public class BlogController {




    @Autowired
    BlogService blogService;


    @ResponseBody
    @PostMapping("/add")
    public Object insert (BBlog blog) {
        blogService.insertBlog(blog);
        return new Object();
    }


    @ResponseBody
    @PostMapping(value = "/getblogs")
    public HashMap<String, Object> getblogs () {
        HashMap<String, Object> returnResult = new HashMap<>();
        returnResult.put("data",blogService.queryBlogList(null));
        return returnResult;
    }












}