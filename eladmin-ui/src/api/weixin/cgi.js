import request from '@/utils/request'

export function getUserList(params) {
  return request({
    url: 'api/weixin/user/list/' + params.department_id + '/' + params.fetch_child,
    method: 'get'
  })
}
export function getDeptList(params) {
  return request({
    url: 'api/weixin/department/list/' + params.id,
    method: 'get'
  })
}
export default { getUserList, getDeptList }
