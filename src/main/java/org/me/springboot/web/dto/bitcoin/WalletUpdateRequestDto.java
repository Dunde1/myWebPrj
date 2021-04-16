package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.bitcoin.Coins;

@Getter
@NoArgsConstructor
public class WalletUpdateRequestDto {
    private Long cash;
    private Long coinAmount;
    private Coins coins;

    @Builder
    public WalletUpdateRequestDto(Long cash, Long coinAmount, Coins coins){
        this.cash = cash;
        this.coinAmount = coinAmount;
        this.coins = coins;
    }
}
