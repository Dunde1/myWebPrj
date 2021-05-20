package org.me.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.me.springboot.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customoAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()   //csrf 보안 설정 비활성화
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()    //보호된 리소스 URI에 접근할 수 있는 권한을 설정
                    .antMatchers("/css/**", "/img/**", "/js/**", "/scss/**", "/vendor/**").permitAll()    //resources
                    .antMatchers("/", "/login", "/error").permitAll()
                    .antMatchers("/api/v1/**", "/function/lotto", "/function/bitcoin", "/posts/list").hasRole(Role.USER.name())
                    .anyRequest().permitAll() //authenticated() -> 인증된 사용자만 사용가능
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  //로그아웃 시 이동할 페이지
                .and()
                    .oauth2Login()
                        .loginPage("/login") //login
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customoAuth2UserService);
    }
}
/*
    @EnableWebSecurity : config 클래스에 달면 SpringSecurityFilterChain이 자동으로 포함됨.
 */
