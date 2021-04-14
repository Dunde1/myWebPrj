package org.me.springboot.web.dto.lotto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LottoTestDto {
    private String id;
    private String email;

    @Builder
    public LottoTestDto(String id, String email){
        this.id = id;
        this.email = email;
    }
}
