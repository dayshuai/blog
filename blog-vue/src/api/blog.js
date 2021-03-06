import request from '@/utils/request'
import qs from 'qs';

export default {
  getHotBlog() {
    return request({
      url: '/blog/blog/hotBlog',
      method: 'get'
    })
  },
  getStatisticalBlogByMonth() {
    return request({
      url: '/blog/statisticalBlogByMonth',
      method: 'get'
    })
  },
  getBlogHome(page, showCount) {
    return request({
      url: '/blog/blog/getBlogs',
      method: 'post',
      data:qs.stringify({'pageNum':page,'pageSize':showCount})
    })
  },
  getBlogById(id, isClick) {
    return request({
      url: '/blog/blog/getBlog',
      method: 'post',
      data:qs.stringify({'id':id})
    })
  },
  getMyBlog(page, showCount) {
    return request({
      url: '/blog/blog/myblog',
      method: 'post',
      data:qs.stringify({'pageNum':page,'pageSize':showCount})
    })
  },
  sendBlog(blogTitle, blogBody, tagId) {  //发布博客
    // alert(qs.stringify({'blogTitle': blogTitle, 'blogBody': blogBody,'tagId':tagId}))
    return request({
      url: '/blog/blog/add',
      method: 'post',
      data: qs.stringify({'title': blogTitle, 'content': blogBody, 'tagIds': tagId})
    })
  },
  uploadImg(formdata) {
    return request({
      url: '/blog/blog/uploadImg',
      method: 'post',
      data: formdata,
      headers: {'Content-Type': 'multipart/form-data'},
    })
  },
  editBlog(blogId, blogTitle, blogBody, tagId) {  //发布博客
    return request({
      url: '/blog/blog/updateBlog/' + blogId,
      method: 'put',
      data: qs.stringify({'title': blogTitle, 'content': blogBody, 'tagIds': tagId})
    })
  },
  adminDeleteBlog(blogId) { //管理员删除博客
    return request({
      url: '/blog/admin/' + blogId,
      method: 'delete'
    })
  },
  userDeleteBlog(blogId) { //普通用户删除博客
    return request({
      url: '/blog/blog/deleteBlog/' + blogId,
      method: 'delete'
    })
  },
  adminGetBlog(page, showCount) {
    return request({
      url: '/blog/AllBlog/' + page + '/' + showCount,
      method: 'get'
    })
  },
  adminSearchBlog(searchTxt, page, showCount) {
    return request({
      url: '/blog/searchAllBlog/' + page + '/' + showCount + '?search=' + searchTxt,
      method: 'get'
    })
  },
  userSearchBlog(searchTxt, page, showCount) {
    return request({
      url: '/blog/searchBlog/' + page + '/' + showCount + '?search=' + searchTxt,
      method: 'get'
    })
  }
}
