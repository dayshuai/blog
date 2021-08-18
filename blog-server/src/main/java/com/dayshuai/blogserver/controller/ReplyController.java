package com.dayshuai.blogserver.controller;

import com.dayshuai.blog.service.ReplyService;
import com.dayshuai.common.entity.AjaxResult;
import com.dayshuai.common.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blog/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private FormatUtil formatUtil;

    @PostMapping("/{discussId}")
    public AjaxResult reply(@PathVariable Integer discussId, String replyBody, Integer rootId) {
        replyService.saveReply(discussId, replyBody, rootId);
        return AjaxResult.success();
    }

    @DeleteMapping("/{replyId}")
    public AjaxResult deleteReply(@PathVariable Integer replyId) {

        replyService.deleteReply(replyId);
        return AjaxResult.success();
    }

    @DeleteMapping("/admin/{replyId}")
    public AjaxResult adminDeleteDiscuss(@PathVariable Integer replyId) {

        replyService.adminDeleteReply(replyId);

        return AjaxResult.success();
    }
}
