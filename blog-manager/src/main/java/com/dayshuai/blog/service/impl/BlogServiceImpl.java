package com.dayshuai.blog.service.impl;

import com.dayshuai.blog.dao.BBlogMapper;
import com.dayshuai.blog.dao.TagMapper;
import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.dto.Tag;
import com.dayshuai.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public BBlog getBlogById(long id) {
        BBlog blog = bBlogMapper.selectByPrimaryKey(id);
        blog.setTags(tagMapper.findTagByBlogId(blog.getId()));
        return blog;
    }

    @Override
    public void insertBlog(BBlog blog, Integer[] tagIds) {
        bBlogMapper.insertSelective(blog);
        Arrays.stream(tagIds).forEach(x-> {
            bBlogMapper.saveBlogTag(blog.getId(), x);
        });

    }

    @Override
    public List<BBlog> queryBlogList(BBlog bBlog) {
        List<BBlog> blogList = bBlogMapper.selectBlogList(bBlog);
        blogList.stream().forEach(x->x.setTags(tagMapper.findTagByBlogId(x.getId())));
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
}
