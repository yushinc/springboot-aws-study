package com.yushin.book.config.auth;

import com.yushin.book.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
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

    private final HttpSession httpSession;

    // 컨트롤러 메소드의 특정 파라미터를 지원하는지 판단
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 @LoginUser 어노테이션이 존재하는지 판단
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터 클래스 타입이 SessionUser.class 인지 판단
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    // 파라미터에 전달할 객체 생성
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 세션에서 직접 user 객체를 가져옴
        return httpSession.getAttribute("user");
    }
}
