package com.dayshuai.blog.service.impl;

import com.dayshuai.blog.dao.BBlogMapper;
import com.dayshuai.blog.dao.TagMapper;
import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import com.dayshuai.bloguser.dao.UUserMapper;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.common.config.ImgUploadConfig;
import com.dayshuai.common.utils.FileUtil;
import com.dayshuai.common.utils.FormatUtil;
import com.dayshuai.common.utils.JwtTokenUtil;
import com.dayshuai.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName : BlogServiceImpl
 * @Description : blogserviceImpl
 * @Author : dayshuai
 * @Date: 2021-07-29 13:52
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BBlogMapper bBlogMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    UUserMapper userMapper;

    @Autowired
    private FormatUtil formatUtil;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private UUIDUtil uuidUtil;

    @Autowired
    private ImgUploadConfig imgUploadConfig;




    @Override
    public BBlog getBlogById(long id) {
        BBlog blog = bBlogMapper.selectByPrimaryKey(id);
        blog.setTags(tagMapper.findTagByBlogId(blog.getId()));
        return blog;
    }

    @Override
    public void insertBlog(BBlog blog, Integer[] tagIds) {

        UUser user = userMapper.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        blog.setAuthorId(user.getId());
        blog.setUserName(user.getUserName());
        bBlogMapper.insertSelective(blog);
        Arrays.stream(tagIds).forEach(x-> {
            bBlogMapper.saveBlogTag(blog.getId(), x);
        });

    }

    @Override
    public List<BBlog> queryBlogList(BBlog bBlog) {
        List<BBlog> blogList = bBlogMapper.selectBlogList(bBlog);
        blogList.stream().forEach(x-> {
            x.setTags(tagMapper.findTagByBlogId(x.getId()));
        });
        return blogList;
    }

    @Override
    public int deleteBlog(Long id) {
        return bBlogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateBlog(Long blogId, String title, String content, Integer[] tagIds) {
        BBlog blog = bBlogMapper.selectByPrimaryKey(blogId);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setMdfTime(new Date());
        bBlogMapper.updateByPrimaryKeyWithBLOBs(blog);
        bBlogMapper.deleteTagsById(blogId);
        for (int tagId : tagIds) {
            bBlogMapper.saveBlogTag(blogId, tagId);
        }
    }

    @Override
    public List<BBlog> queryBlogByUser() {

        UUser user = userMapper.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        List<BBlog> blogs = bBlogMapper.queryBlobByUser(user.getId());
        blogs.stream().forEach(x->x.setTags(tagMapper.findTagByBlogId(x.getId())));
        return blogs;
    }

    @Override
    public synchronized String saveImg(MultipartFile file) throws IOException {

        //获取图片格式/后缀
        String format = formatUtil.getFileFormat(file.getOriginalFilename());
        //获取图片保存路径
        String savePath = fileUtil.getSavePath();
        //存储已满
        if (!formatUtil.checkStringNull(savePath)) {
            throw new IOException("存储已满 请联系管理员");
        }
        //保存图片
        String fileName = uuidUtil.generateUUID() + format;
        File diskFile = new File(savePath + "/" + fileName);
        file.transferTo(diskFile);
        //将硬盘路径转换为url，返回
        return imgUploadConfig.getStaticAccessPath().replaceAll("\\*", "") + fileName;
    }
}
