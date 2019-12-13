import request from '@/utils/request'

export function userList(param) {
  return request({
    url: '/sys/user/list',
    method: 'post',
    data: param || {}
  })
}

export function getInfo() {
  return request({
    url: '/sys/user/info',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}

export function addUser(param) {
  return request({
    url: '/sys/user/insert',
    method: 'post',
    data: param || {}
  })
}
export function editUser(param) {
  return request({
    url: '/sys/user/edit',
    method: 'post',
    data: param || {}
  })
}
export function deleteUser(param) {
  return request({
    url: '/sys/user/delete',
    method: 'post',
    data: param || {}
  })
}
