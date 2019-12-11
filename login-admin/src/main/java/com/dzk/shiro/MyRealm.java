package com.dzk.shiro;

import com.dzk.model.UserAdmin;
import com.dzk.model.UserPermission;
import com.dzk.service.UserAdminService;
import com.dzk.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserAdminService userAdminService;
    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("myRealm 授权");
        UserAdmin userAdmin = (UserAdmin)principalCollection.getPrimaryPrincipal();
        Long id = userAdmin.getId();
        System.out.println("myRealm userId : "+id);
        //用户权限列表
        List<String> userPermissionList = userAdminService.getPermissionList(id);

        System.out.println("myRealm 权限列表 ： "+userPermissionList);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(userPermissionList);
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        System.out.println("myRealm token : "+ token);
        // 解密获得username，用于和数据库进行对比
        String username = jwtUtil.getUserNameFromToken(token);
        System.out.println("MyRealm username : "+ username);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        UserAdmin userAdmin = userAdminService.queryByName(username);
        System.out.println("useradmin : "+userAdmin.toString());
        if(!jwtUtil.validateToken(token, userAdmin)){
            throw new IncorrectCredentialsException("token 校验失败，请重新登陆");
        }

        if(userAdmin.getStatus() != 0){
            throw new LockedAccountException("账号锁定");
        }
        System.out.println("myRealm 认证结束");

        return new SimpleAuthenticationInfo(userAdmin, token, getName());
    }
}
