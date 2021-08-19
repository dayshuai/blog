import request from '@/utils/request'

export default {
  getSite () {
    return request({
      url: '/blog/site',
      method: 'post'
    })
  }
}
