import request from '@/utils/request'

export function permissionList() {
  return request({
    url: '/sys/user/permissionList',
    method: 'get',
  })
}
export function permissionListByPId(param) {
  return request({
    url: '/sys/user/permissionList/'+param,
    method: 'get',
  })
}
export function permissionListById(param) {
  return request({
    url: '/sys/user/permission/'+param,
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
export function addPermission(param) {
  return request({
    url: '/sys/user/insertPermission',
    method: 'post',
    data: param || {}
  })
}
export function editPermission(param) {
  return request({
    url: '/sys/user/editPermission',
    method: 'post',
    data: param || {}
  })
}

export function deletePermission(param) {
  return request({
    url: '/sys/user/deletePermission',
    method: 'post',
    data: param || {}
  })
}