package com.dayshuai.blogserver.controller;

import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import com.dayshuai.bloguser.dao.URoleMapper;
import com.dayshuai.bloguser.service.UserService;
import com.dayshuai.common.controller.BaseController;
import com.dayshuai.common.entity.AjaxResult;
import com.dayshuai.common.page.TableDataInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @ClassName : BlogController
 * @Description :
 * @Author : dayshuai
 * @Date: 2021-07-17 20:16
 */
@Controller
@RequestMapping(value = "/blog/blog")
public class BlogController extends BaseController {




    @Autowired
    BlogService blogService;


    @ResponseBody
    @PostMapping(value = "/add", produces = "application/json; charset=utf-8")
    public AjaxResult insert (BBlog blog, Integer[] tagIds) {

        blogService.insertBlog(blog, tagIds);
        return AjaxResult.success();
    }


    @PutMapping("updateBlog/{blogId}")
    public AjaxResult updateBlog(@PathVariable Long blogId, String title, String content, Integer[] tagIds) {
            blogService.updateBlog(blogId, title, content, tagIds);
            return AjaxResult.success();

    }


    @ResponseBody
    @PostMapping(value = "/getBlogs")
    public TableDataInfo getblogs () {
        startPage();
        return getDataTable(blogService.queryBlogList(null));
    }



    @ResponseBody
    @PostMapping(value = "/getBlog")
    public AjaxResult getblog (Long id) {
        return AjaxResult.success(blogService.getBlogById(id));
    }


    @ResponseBody
    @DeleteMapping(value = "/deleteBlog")
    public AjaxResult deleteBlog (Long id) {
        return AjaxResult.success(blogService.deleteBlog(id));
    }

    /**
     * 根据用户分页查询博文
     *

     * @return
     */
    @PostMapping("/myblog")
    @ResponseBody
    public TableDataInfo findBlogByUser() {
        startPage();
        return getDataTable(blogService.queryBlogByUser());
    }







}
