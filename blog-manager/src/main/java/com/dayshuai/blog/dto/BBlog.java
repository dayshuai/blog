package com.dayshuai.blog.dto;

import java.util.Date;

public class BBlog {
    private Long id;

    private String title;

    private Date createTime;

    private Date mdfTime;

    private Long authorId;

    private Long likesNum;

    private Integer blogDiscusscount;

    private Integer blogBlogviews;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getMdfTime() {
        return mdfTime;
    }

    public void setMdfTime(Date mdfTime) {
        this.mdfTime = mdfTime;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Long likesNum) {
        this.likesNum = likesNum;
    }

    public Integer getBlogDiscusscount() {
        return blogDiscusscount;
    }

    public void setBlogDiscusscount(Integer blogDiscusscount) {
        this.blogDiscusscount = blogDiscusscount;
    }

    public Integer getBlogBlogviews() {
        return blogBlogviews;
    }

    public void setBlogBlogviews(Integer blogBlogviews) {
        this.blogBlogviews = blogBlogviews;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}