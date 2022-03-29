import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/cm/file',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/cm/file/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/cm/file',
    method: 'put',
    data
  })
}

export function delById(id) {
  console.log('*****************')
  console.log(id)
  return request({
    url: 'api/cm/file/' + id,
    method: 'delete'
  })
}

export default { add, edit, del, delById }
