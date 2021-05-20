package org.me.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    /** 스프링의 Audit 기능을 활용하기 위한 설정 클래스
     *
     * audit 목적으로 거의 모든 엔티티에 들어가는 컬럼을 작성하기 위함.
     */
}
