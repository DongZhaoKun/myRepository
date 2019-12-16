import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/sys/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo() {
  return request({
    url: '/sys/user/info',
    method: 'get',
    // data: param
  })
}

export function logout() {
  return request({
    url: '/sys/user/logout',
    method: 'get'
  })
}
