import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/cm/side',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/cm/side/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/cm/side',
    method: 'put',
    data
  })
}

export function list() {
  return request({
    url: 'api/cm/side/list',
    method: 'get'
  })
}

export function uploads(data) {
  return request({
    url: '/api/common/uploads',
    method: 'post',
    data
  })
}

export default { add, edit, del, list, uploads }
