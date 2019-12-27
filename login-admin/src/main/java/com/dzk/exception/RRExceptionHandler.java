/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.dzk.exception;

import com.dzk.utils.CommonResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());



//	@ExceptionHandler(AuthorizationException.class)
//	public CommonResult handleAuthorizationException(AuthorizationException e){
//		logger.error(e.getMessage(), e);
//		return CommonResult.unauthorized(e.getMessage());
//	}
//
//	@ExceptionHandler(Exception.class)
//	public CommonResult handleException(Exception e){
//		logger.error(e.getMessage(), e);
//		return CommonResult.failed(e.getMessage());
//	}

	// 捕捉shiro的异常
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ShiroException.class)
	public CommonResult handle401(ShiroException e) {
		return  CommonResult.unauthorized(e.getMessage(), null);
	}

	// 捕捉UnauthorizedException
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthorizationException.class)
	public CommonResult handle401() {
		return  CommonResult.unauthorized( "权限不足，请联系系统管理员！", null);
	}

	// 捕捉其他所有异常
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult globalException(HttpServletRequest request, Throwable ex) {
		ex.printStackTrace();
		return   CommonResult.failed( ex.getMessage(), null);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}
}
