import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/rpItem',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/rpItem/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/rpItem',
    method: 'put',
    data
  })
}

export function importTemplate(type) {
  return request({
    url: 'api/rpItem/importTemplate/' + type,
    method: 'get'
  })
}

// 导入数据
export function importData() {
  return request({
    url: 'api/rpItem/importData/',
    method: 'get'
  })
}

export default { add, edit, del, importTemplate, importData }
