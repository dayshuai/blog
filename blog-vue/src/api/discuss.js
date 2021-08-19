import request from '@/utils/request'
import qs from 'qs';

export default {
  getNewDiscuss() {
    return request({
      url: '/blog/discuss/newDiscuss',
      method: 'get'
    })
  },
  getDiscussByBlogId(blogId, page, showCount) {
    return request({
      url: '/blog/discuss/getByBlog/' + blogId,
      method: 'post',
      data:qs.stringify({'pageNum':page,'pageSize':showCount})
    })
  },
  sendDiscuss(blogId, discussBody) {  //发送评论
    return request({
      url: '/blog/discuss/sendDiscuss/' + blogId,
      method: 'post',
      data: qs.stringify({'discussBody': discussBody})
    })
  },
  adminDeleteDiscuss(discussId) { //管理员删除评论
    return request({
      url: '/discuss/admin/' + discussId,
      method: 'delete'
    })
  },
  userDeleteDiscuss(discussId) { //用户删除评论
    return request({
      url: '/blog/discuss/' + discussId,
      method: 'delete'
    })
  },
  adminDeleteReply(replyId) { //管理员删除回复
    return request({
      url: '/reply/admin/' + replyId,
      method: 'delete'
    })
  },
  userDeleteReply(replyId) { //用户删除回复
    return request({
      url: '/blog/reply/' + replyId,
      method: 'delete'
    })
  },
  getUserNewDiscuss(){ //用户删除回复
    return request({
      url: '/discuss/userNewDiscuss',
      method: 'get'
    })
  },


}
