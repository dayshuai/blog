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

@Service
public class ReplyService {

    @Autowired
    private ReplyMapper replyDao;

    @Autowired
    private UUserMapper userDao;

    @Autowired
    private BBlogMapper blogDao;

    @Autowired
    private DiscussMapper discussDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private HttpServletRequest request;

    /**
     * 保存回复
     *
     * @param discussId
     * @param replyBody
     * @param rootId    可为null
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveReply(Integer discussId, String replyBody, Integer rootId) {
        UUser user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        Reply reply = new Reply();
        Discuss discuss = discussDao.findDiscussById(discussId);
        if (discuss == null) {
            throw new RuntimeException("评论不存在");
        }

        reply.setDiscuss(discuss);
        reply.setUser(user);
        reply.setBody(replyBody);
        reply.setTime(dateUtil.getCurrentDate());
        Reply rootReply = new Reply();
        rootReply.setId(rootId);
        reply.setReply(rootReply);
        replyDao.saveReply(reply);

        //更新博文评论数
        BBlog blog = blogDao.selectByPrimaryKey(discuss.getBlog().getId());
        blog.setBlogDiscusscount(blog.getBlogDiscusscount() + 1);
        blogDao.updateByPrimaryKeySelective(blog);

    }

    /**
     * 删除回复
     *
     * @param replyId
     */
    public void deleteReply(Integer replyId) {
        UUser user = userDao.findUserByName(jwtTokenUtil.getUsernameFromRequest(request));
        Reply reply = replyDao.findReplyById(replyId);
        if (reply == null) {
            throw new RuntimeException("回复不存在");
        }

        if (!user.getId().equals(reply.getUser().getId())) {
            throw new RuntimeException("无权删除");
        }

        //删除回复
        replyDao.deleteReplyById(replyId);

        Discuss discuss = discussDao.findDiscussById(reply.getDiscuss().getId());
        //更新博文评论数
        BBlog blog = blogDao.selectByPrimaryKey(discuss.getBlog().getId());
        blog.setBlogDiscusscount(blog.getBlogDiscusscount() + 1);
        blogDao.updateByPrimaryKeySelective(blog);

    }

    /**
     * 管理员删除回复
     *
     * @param replyId
     */
    public void adminDeleteReply(Integer replyId) {
        Reply reply = replyDao.findReplyById(replyId);
        if (reply == null) {
            throw new RuntimeException("回复不存在");
        }
        //删除回复
        replyDao.deleteReplyById(replyId);

        Discuss discuss = discussDao.findDiscussById(reply.getDiscuss().getId());
        //更新博文评论数
        BBlog blog = blogDao.selectByPrimaryKey(discuss.getBlog().getId());
        blog.setBlogDiscusscount(blog.getBlogDiscusscount() + 1);
        blogDao.updateByPrimaryKeySelective(blog);
    }
}
