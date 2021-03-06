package com.dayshuai.blog.dao;

import com.dayshuai.blog.dto.Reply;

import java.util.List;


public interface ReplyMapper {
    /**
     * 保存回复
     * @param reply
     */
    void saveReply(Reply reply);

    /**
     * 根据id查询回复
     * @param replyId
     * @return
     */
    Reply findReplyById(Integer replyId);

    /**
     * 根据id删除回复
     * @param replyId
     */
    void deleteReplyById(Integer replyId);

    /**
     * 根据评论id查询回复
     * @param id
     * @return
     */
    List<Reply> findReplyByDiscussId(Integer id);

    /**
     * 根据评论id删除回复
     * @param discussId
     * @return 受影响行数
     */
    Integer deleteReplyByDiscussId(Integer discussId);
}
