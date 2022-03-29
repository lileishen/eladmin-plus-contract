import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/tools/weixin',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/tools/weixin/',
    method: 'delete',
    data: ids
  })
}

export function update(data) {
  return request({
    url: 'api/tools/weixin',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/tools/weixin',
    method: 'get'
  })
}

export function send(data) {
  return request({
    url: 'api/tools/weixin',
    data,
    method: 'post'
  })
}

export default { add, update, del, get, send }
