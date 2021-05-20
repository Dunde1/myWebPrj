package org.me.springboot.config.auth.dto;

import lombok.Getter;
import org.me.springboot.domain.user.User;

@Getter
public class SessionUser {
    /** 유저의 세션정보를 저장하기 위한 dto 클래스
     *
     * name : 유저 이름
     * email : 유저 이메일
     * picture : 유저 프로필 사진
     */
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
