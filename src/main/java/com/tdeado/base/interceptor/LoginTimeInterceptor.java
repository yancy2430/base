package com.tdeado.base.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginTimeInterceptor extends HandlerInterceptorAdapter {


    //在控制器执行前调用  
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
//        System.out.println("执行afterCompletion方法-->01");
//        try {
//            Cookie cookie = CookieUtils.getCookieFromCookies(request.getCookies(), "jarkey");
//            if (null != cookie && !"".equals(cookie.getValue())) {
//                Object obj = request.getSession().getAttribute(cookie.getValue());
//                if (null != obj) {
//                    System.out.println("登录成功");
//                    return true;
//                } else {
//                    throw new RuntimeException(StringText.YOU_HAVE_NOT_LOGIN);
//                }
//            } else {
//                throw new RuntimeException(StringText.YOU_HAVE_NOT_LOGIN);
//            }
//
//        }catch (Exception e){
//            throw new RuntimeException(StringText.YOU_HAVE_NOT_LOGIN);
//        }

        return  true;
    }

    //在后端控制器执行后调用
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("执行postHandle方法-->02");
        super.postHandle(request, response, handler, modelAndView);
    }

    //整个请求执行完成后调用  
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("执行afterCompletion方法-->03");
        super.afterCompletion(request, response, handler, ex);
    }
}  