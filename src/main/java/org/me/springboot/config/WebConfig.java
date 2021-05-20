package org.me.springboot.config;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /** 웹 MVC 설정을 담당하는 클래스
     *
     * loginUserArgumentResolver :
     * addArgumentResolvers() : 모든 HandlerMethodArgumentResolver객체를 갖고 있는 리스트에 새로운 객체를 추가하여 담는다.
     *      파라미터 값에 어노테이션을 통해 의존성 주입을 할 수 있다.
     */
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
/* WebMvcConfigurer : @EnableWebMvc 어노테이션이 자동으로 설정해주는 세팅값에 사용자가 원하는 세팅을 추가할 수 있게됨.
 * HandlerMethodArgumentResolver : 컨트롤러 메서드에서 특정 조건에 맞는 파라미터가 있을 때 원하는 값을 바인딩해주는 인터페이스. (ex> @RequestBody)
 */