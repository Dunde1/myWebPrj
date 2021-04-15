package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class CoinInfosWebClientDto {
    private Map<String, String> result;
    private Map<String, String> allowance;

    @Builder
    public CoinInfosWebClientDto(Map result, Map allowance) {
        this.result = result;
        this.allowance =allowance;
    }
}
