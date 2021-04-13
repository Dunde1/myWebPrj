package org.me.springboot.web.dto.lotto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LottoListRequestDto {
    String lnum1, lnum2, lnum3, lnum4, lnum5, lnum6;

    @Builder
    public LottoListRequestDto(String lnum1, String lnum2, String lnum3, String lnum4, String lnum5, String lnum6){
        this.lnum1 = lnum1;
        this.lnum2 = lnum2;
        this.lnum3 = lnum3;
        this.lnum4 = lnum4;
        this.lnum5 = lnum5;
        this.lnum6 = lnum6;
    }
}
