package com.dayshuai.blog.service.impl;

import com.dayshuai.blog.dao.BBlogMapper;
import com.dayshuai.blog.dao.TagMapper;
import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.service.BlogService;
import com.dayshuai.bloguser.dao.UUserMapper;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.common.config.ImgUploadConfig;
import com.dayshuai.common.config.RedisConfig;
import com.dayshuai.common.utils.FileUtil;
import com.dayshuai.common.utils.FormatUtil;
import com.dayshuai.common.utils.JwtTokenUtil;
import com.dayshuai.common.utils.UUIDUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public BBlog getBlogById(long id) {
        BBlog blog = bBlogMapper.selectByPrimaryKey(id);
        UUser user = userMapper.selectByPrimaryKey(blog.getAuthorId());
        blog.setUserName(user.getUserName());
        blog.setTags(tagMapper.findTagByBlogId(blog.getId()));
        return blog;
    }

    @Override
    public void insertBlog(BBlog blog, Integer[] tagIds) {

        UUser user = userMapper.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        blog.setAuthorId(user.getId());
        blog.setUserName(user.getUserName());
        bBlogMapper.insertSelective(blog);
        Arrays.stream(tagIds).forEach(x -> {
            bBlogMapper.saveBlogTag(blog.getId(), x);
        });

    }

    @Override
    public List<BBlog> queryBlogList(BBlog bBlog) {



        List<BBlog> blogList = bBlogMapper.selectBlogList(bBlog);
        blogList.stream().forEach(x -> {
            x.setTags(tagMapper.findTagByBlogId(x.getId()));
            String body = x.getContent();
            if (body.length() > MAX_BODY_CHAR_COUNT) {
                x.setContent(body.substring(0, MAX_BODY_CHAR_COUNT));
            }
        });

        return blogList;
    }

    private static final int MAX_BODY_CHAR_COUNT = 150;

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
        blogs.stream().forEach(x -> x.setTags(tagMapper.findTagByBlogId(x.getId())));
        return blogs;
    }

    @Override
    public synchronized String saveImg(MultipartFile file) throws IOException {

        //??????????????????/??????
        String format = formatUtil.getFileFormat(file.getOriginalFilename());
        //????????????????????????
        String savePath = fileUtil.getSavePath();
        //????????????
        if (!formatUtil.checkStringNull(savePath)) {
            throw new IOException("???????????? ??????????????????");
        }
        //????????????
        String fileName = uuidUtil.generateUUID() + format;
        File diskFile = new File(savePath + "/" + fileName);
        file.transferTo(diskFile);
        //????????????????????????url?????????
        return imgUploadConfig.getStaticAccessPath().replaceAll("\\*", "") + fileName;
    }

    /**
     * ??????????????????
     * ????????????
     *
     * @return
     */
    @Override
    public List<BBlog> findHotBlog() throws IOException {
        // ??????redis ????????????id set

        if (redisTemplate.hasKey(RedisConfig.REDIS_HOT_BLOG)) {

            // ?????????
            List<BBlog> blogList = new ArrayList<>(6);

            List<String> blogIdList = redisTemplate.opsForList().range(RedisConfig.REDIS_HOT_BLOG, 0, RedisConfig.REDIS_HOT_BLOG_COUNT);

            for (String blogId : blogIdList) {
                //?????????????????? blog
                String blogJson = redisTemplate.opsForValue().get(RedisConfig.REDIS_BLOG_PREFIX + blogId);
                // ?????? ??????
                BBlog blog = objectMapper.readValue(blogJson, BBlog.class);
                blogList.add(blog);
            }


            return blogList;
        } else {
            // redis??????????????? ?????? mysql
            // ????????????????????????????????????????????????
            return bBlogMapper.findHotBlog(6);
        }


    }

    @Override
    public int getBlogLikeCountByBlogId(Integer blogId) {
        int likeCount;
        String likeCountKey = String.valueOf(blogId);
        //?????????????????????????????????
        if (!redisTemplate.opsForHash().hasKey(RedisConfig.MAP_BLOG_LIKE_COUNT_KEY, likeCountKey)) {
            likeCount = bBlogMapper.getBlogLikeCountByBlogId(blogId);
            redisTemplate.opsForHash().put(RedisConfig.MAP_BLOG_LIKE_COUNT_KEY, likeCountKey, String.valueOf(likeCount));
        } else {
            likeCount = Integer.parseInt((String) redisTemplate.opsForHash().get(RedisConfig.MAP_BLOG_LIKE_COUNT_KEY, likeCountKey));
        }
        return likeCount;
    }


}
