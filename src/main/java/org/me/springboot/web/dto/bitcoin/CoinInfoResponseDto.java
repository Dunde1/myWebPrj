package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoinInfoResponseDto {
    private String btcPrice;
    private String bchPrice;
    private String btgPrice;
    private String eosPrice;
    private String etcPrice;
    private String ethPrice;
    private String ltcPrice;
    private String xrpPrice;

    @Builder
    public CoinInfoResponseDto(String btcPrice, String bchPrice, String btgPrice, String eosPrice,
                               String etcPrice, String ethPrice, String ltcPrice, String xrpPrice){
        this.btcPrice = btcPrice;
        this.bchPrice = bchPrice;
        this.btgPrice = btgPrice;
        this.eosPrice = eosPrice;
        this.etcPrice = etcPrice;
        this.ethPrice = ethPrice;
        this.ltcPrice = ltcPrice;
        this.xrpPrice = xrpPrice;
    }
}
