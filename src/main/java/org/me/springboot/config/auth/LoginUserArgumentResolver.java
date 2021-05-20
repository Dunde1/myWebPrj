package org.me.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.dto.SessionUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    /** 로그인 유저의 정보를 담을 Resolver 클래스 생성
     *
     * httpSession : 세션 정보 받아오기
     *
     * supportsParameter() : 현재 파라미터가 resolver를 지원하는지 여부를 리턴.
     *      파라미터에 @LoginUser가 존재하고 SessionUser 클래스와 타입이 같다면 True 반환.
     * resolveArgument() : 실제 바인딩 할 객체를 리턴.
     *      httpSession에 저장된 Attribute "user" 를 리턴, 해당 어노테이션(LoginUser)이 붙은 파라미터에 값을 전닿한다.
     */
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
