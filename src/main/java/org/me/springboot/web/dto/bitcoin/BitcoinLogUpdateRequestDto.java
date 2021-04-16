package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.bitcoin.Coins;

@Getter
@NoArgsConstructor
public class BitcoinLogUpdateRequestDto {
    private Coins coins;
    private Long amount;
    private Long value;

    @Builder
    public BitcoinLogUpdateRequestDto(Coins coins, Long amount, Long value){
        this.coins = coins;
        this.amount = amount;
        this.value = value;
    }
}
