package com.dayshuai.blog.service;

import com.dayshuai.blog.dao.BBlogMapper;
import com.dayshuai.blog.dao.DiscussMapper;
import com.dayshuai.blog.dao.ReplyMapper;
import com.dayshuai.blog.dto.BBlog;
import com.dayshuai.blog.dto.Discuss;
import com.dayshuai.blog.dto.Reply;
import com.dayshuai.bloguser.dao.UUserMapper;
import com.dayshuai.bloguser.dto.UUser;
import com.dayshuai.common.utils.DateUtil;
import com.dayshuai.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class DiscussService {
    @Autowired
    private DiscussMapper discussDao;

    @Autowired
    private UUserMapper userDao;

    @Autowired
    private ReplyMapper replyDao;

    @Autowired
    private BBlogMapper blogDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 发布评论
     * 博文评论数加1
     *
     * @param discussBody
     * @param blogId
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDiscuss(String discussBody, Long blogId) {
        UUser user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        BBlog blog = blogDao.selectByPrimaryKey(blogId);
        Discuss discuss = new Discuss();
        discuss.setUser(user);
        discuss.setBody(discussBody);
        discuss.setBlog(blog);
        discuss.setTime(dateUtil.getCurrentDate());
        discussDao.saveDiscuss(discuss);

        //评论数加一
        blog.setBlogDiscusscount(blog.getBlogDiscusscount() + 1);
        blogDao.updateByPrimaryKeySelective(blog);
    }

    /**
     * 删除评论
     * 级联删除评论下的所有回复
     * 博文评论数 - (评论数+回复数)
     *
     * @param discussId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDiscuss(Integer discussId) {
        UUser user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        Discuss discuss = discussDao.findDiscussById(discussId);
        if (discuss == null) {
            throw new RuntimeException("评论不存在");
        }
        if (!user.getId().equals(discuss.getUser().getId())) {
            throw new RuntimeException("无权删除");
        }

        discussDao.deleteDiscussById(discussId);

        //返回受影响行数
        Integer rows = replyDao.deleteReplyByDiscussId(discussId);


        BBlog blog = blogDao.selectByPrimaryKey(discuss.getBlog().getId());
        blog.setBlogDiscusscount(blog.getBlogDiscusscount() - 1 - rows);
        blogDao.updateByPrimaryKeySelective(blog);
    }

    /**
     * 管理员删除评论
     * 博文评论数-1
     *
     * @param discussId
     */
    public void adminDeleteDiscuss(Integer discussId) {
        Discuss discuss = discussDao.findDiscussById(discussId);
        if (discuss == null) {
            throw new RuntimeException("评论不存在");
        }
        discussDao.deleteDiscussById(discussId);

        Integer rows = replyDao.deleteReplyByDiscussId(discussId); //返回受影响行数

        BBlog blog = blogDao.selectByPrimaryKey(discuss.getBlog().getId());
        blog.setBlogDiscusscount(blog.getBlogDiscusscount() - 1 - rows);
        blogDao.updateByPrimaryKeySelective(blog);
    }

    /**
     * 根据博文id查询 该博文下的评论及回复
     *
     * @param blogId
     * @return
     */
    public List<Discuss> findDiscussByBlogId(Long blogId) {

        List<Discuss> discusses = discussDao.findDiscussByBlogId(blogId);

        for (Discuss discuss : discusses) {
            List<Reply> replyList = replyDao.findReplyByDiscussId(discuss.getId());
            for (Reply reply : replyList) {
                if (reply.getReply() != null) {
                    reply.setReply(replyDao.findReplyById(reply.getReply().getId()));
                }
            }
            discuss.setReplyList(replyList);
        }
        return discusses;
    }

    /**
     * 获取博文下评论数量
     *
     * @param blogId
     * @return
     */
    public Long getDiscussCountByBlogId(Integer blogId) {
        return discussDao.getDiscussCountByBlogId(blogId);
    }

    /**
     * 获取最新六条评论
     *
     * @return
     */
    public List<Discuss> findNewDiscuss() {
        return discussDao.findNewDiscuss(6);
    }


    /**
     * 获取用户发布的所有博文下的评论
     *
     * @return
     */
    public List<Discuss> findUserNewDiscuss() {
        UUser user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        return discussDao.findUserNewDiscuss(user.getId(), 6);
    }
}

