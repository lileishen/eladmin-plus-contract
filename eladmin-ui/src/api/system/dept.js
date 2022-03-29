import request from '@/utils/request'

export function getDepts(params) {
  return request({
    url: 'api/dept',
    method: 'get',
    params
  })
}

export function getDeptSuperior(ids) {
  const data = ids.length || ids.length === 0 ? ids : Array.of(ids)
  return request({
    url: 'api/dept/superior',
    method: 'post',
    data
  })
}

export function add(data) {
  return request({
    url: 'api/dept',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/dept',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/dept',
    method: 'put',
    data
  })
}

// 企业微信部门同步
export function wxSync() {
  return request({
    url: 'api/dept/sync',
    method: 'get'
  })
}

export default { add, edit, del, getDepts, getDeptSuperior, wxSync }
