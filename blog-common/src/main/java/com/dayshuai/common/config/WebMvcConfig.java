package com.dayshuai.common.config;


import com.dayshuai.common.utils.FileUtil;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {



    @Autowired
    private ImgUploadConfig imgUploadConfig;

    @Autowired
    private FileUtil fileUtil;




    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {



        //以下注释部分用于生产环境下，文件夹单位较大，层数较多的情况
        //文件上传配置
        File root = new File(imgUploadConfig.getUploadFolder());
        ArrayList<File> files = null;
        //有此文件夹默认上传文件夹初始化过
        if (root.exists()) {
            //将最下层文件夹进行资源映射
            log.info("上传文件夹已存在，资源映射较耗时");
            files = new ArrayList<>(fileUtil.getAllFolder());
        } else { //没有被初始化过
            log.info("上传文件夹初始化中");
            files = new ArrayList<>(fileUtil.initUploadFolder());

        }
        // 将生成的文件夹进行资源映射
        ConcurrentLinkedQueue<File> availablePath = ImgUploadConfig.getAvailablePath();
        String[] paths = new String[files.size()];
        for (int i = 0; i < paths.length; i++) {
            File file = files.get(i);
            paths[i] = "file:" + file.getPath() + "/";
            if (file.listFiles().length < imgUploadConfig.getFolderSize()) {
                availablePath.add(file);
            }
        }
        log.info("资源文件夹映射完成");
        registry.addResourceHandler(imgUploadConfig.getStaticAccessPath())
                .addResourceLocations(paths);

    }


}
