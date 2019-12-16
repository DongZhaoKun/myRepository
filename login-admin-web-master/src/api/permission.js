import request from '@/utils/request'

export function permissionList() {
  return request({
    url: '/sys/user/permissionList',
    method: 'get',
  })
}
export function userRole(param) {
  return request({
    url: '/sys/user/userRole/'+param,
    method: 'post',
    // data: param
  })
}
export function addRole(param) {
  return request({
    url: '/sys/user/insertRole',
    method: 'post',
    data: param || {}
  })
}
export function editRole(param) {
  return request({
    url: '/sys/user/editRole',
    method: 'post',
    data: param || {}
  })
}
export function deleteRole(param) {
  return request({
    url: '/sys/user/deleteRole',
    method: 'post',
    data: param || {}
  })
}
export function insertAR(param) {
  return request({
    url: '/sys/user/insertAR',
    method: 'post',
    data: param || {}
  })
}