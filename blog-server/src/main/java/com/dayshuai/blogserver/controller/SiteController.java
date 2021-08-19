package com.dayshuai.blogserver.controller;

import com.dayshuai.common.config.SiteIntroductionConfig;
import com.dayshuai.common.entity.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/site")
public class SiteController {

    @Autowired
    private SiteIntroductionConfig siteIntroductionConfig;


    @PostMapping
    public AjaxResult getIntroduction() {

        return AjaxResult.success(siteIntroductionConfig.getIntroduction());
    }


}
