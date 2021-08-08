package com.dayshuai.blogserver.controller;

import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import com.dayshuai.blog.service.TagService;
import com.dayshuai.common.controller.BaseController;
import com.dayshuai.common.entity.AjaxResult;
import com.dayshuai.common.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : TagController
 * @Description :
 * @Author : dayshuai
 * @Date: 2021-07-17 20:16
 */
@RestController
@RequestMapping("/tag")
public class TagController extends BaseController {




    @Autowired
    TagService tagService;


    @GetMapping("/allTags")
    public TableDataInfo getAllTags() {
        return getDataTable(tagService.queryTagList(null));
    }



}
