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
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/css/**", "/img/**", "/js/**", "/scss/**", "/vendor/**").permitAll()    //resources
                    .antMatchers("/", "/login", "/error").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().permitAll() //authenticated() //인증된 사용자만 사용가능
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .loginPage("/login") //login
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customoAuth2UserService);
    }
}
