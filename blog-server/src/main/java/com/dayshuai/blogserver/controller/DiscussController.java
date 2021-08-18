package com.dayshuai.blogserver.controller;

import com.dayshuai.blog.service.DiscussService;
import com.dayshuai.common.controller.BaseController;
import com.dayshuai.common.entity.AjaxResult;
import com.dayshuai.common.page.TableDataInfo;
import com.dayshuai.common.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blog/discuss")
public class DiscussController extends BaseController {

    @Autowired
    private DiscussService discussService;

    @Autowired
    private FormatUtil formatUtil;


    @PostMapping("/sendDiscuss/{blogId}")
    public AjaxResult discuss(String discussBody, @PathVariable Long blogId) {

        discussService.saveDiscuss(discussBody, blogId);
        return AjaxResult.success();
    }


    @DeleteMapping("/{discussId}")
    public AjaxResult deleteDiscuss(@PathVariable Integer discussId) {

        discussService.deleteDiscuss(discussId);
        return AjaxResult.success();
    }

    @DeleteMapping("/admin/{discussId}")
    public AjaxResult adminDeleteDiscuss(@PathVariable Integer discussId) {

        discussService.adminDeleteDiscuss(discussId);
        return AjaxResult.success();

    }

    @PostMapping("/getByBlog/{blogId}")
    public TableDataInfo getDiscussByBlog(@PathVariable Long blogId) {
        startPage();
        return getDataTable(discussService.findDiscussByBlogId(blogId));
    }

    @GetMapping("/newDiscuss")
    public AjaxResult newDiscuss() {
        return AjaxResult.success(discussService.findNewDiscuss());
    }

    @GetMapping("/userNewDiscuss")
    public AjaxResult userNewDiscuss() {
        return AjaxResult.success(discussService.findUserNewDiscuss());
    }


}
