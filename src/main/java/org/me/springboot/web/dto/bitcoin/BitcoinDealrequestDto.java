package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.bitcoin.Coins;

@Getter
@NoArgsConstructor
public class BitcoinDealrequestDto {
    //userEmail
    private String userEmail;
    //bitcoinLog
    private Coins coins;
    private Long amount;
    private Long value;
    //wallet
    private Long cash;
    private Long coinAmount;

    @Builder
    public BitcoinDealrequestDto(String userEmail, Coins coins, Long amount, Long value, Long cash, Long coinAmount){
        this.userEmail = userEmail;
        this.coins = coins;
        this.amount = amount;
        this.value = value;
        this.cash = cash;
        this.coinAmount = coinAmount;
    }
}
