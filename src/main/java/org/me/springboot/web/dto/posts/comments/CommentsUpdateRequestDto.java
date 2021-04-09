package org.me.springboot.web.dto.posts.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsUpdateRequestDto {
    private String comment;

    @Builder
    public CommentsUpdateRequestDto(String comment){
        this.comment = comment;
    }
}
