package com.dayshuai.blogserver.controller;

import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import com.dayshuai.common.controller.BaseController;
import com.dayshuai.common.entity.AjaxResult;
import com.dayshuai.common.page.TableDataInfo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName : BlogController
 * @Description :
 * @Author : dayshuai
 * @Date: 2021-07-17 20:16
 */
@Controller
@RequestMapping(value = "/blog/blog")
@Slf4j
public class BlogController extends BaseController {




    @Autowired
    BlogService blogService;


    @ResponseBody
    @PostMapping(value = "/add", produces = "application/json; charset=utf-8")
    public AjaxResult insert (BBlog blog, Integer[] tagIds) {

        blogService.insertBlog(blog, tagIds);
        return AjaxResult.success();
    }

    @ResponseBody
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
    @DeleteMapping(value = "/deleteBlog/{blogId}")
    public AjaxResult deleteBlog (@PathVariable Long blogId) {
        return AjaxResult.success(blogService.deleteBlog(blogId));
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

    @ResponseBody
    @PostMapping("/uploadImg")
    public AjaxResult uploadImg(MultipartFile file) {

        String url = null;
        try {
            url = blogService.saveImg(file);
        } catch (IOException e) {
            log.error("",e);
            return AjaxResult.error();
        }
        return AjaxResult.success(url);
    }







}
