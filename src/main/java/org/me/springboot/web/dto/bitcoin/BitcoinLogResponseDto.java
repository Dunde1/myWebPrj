package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.bitcoin.BitcoinLog;
import org.me.springboot.domain.bitcoin.Coins;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BitcoinLogResponseDto {

    private LocalDateTime createdDate;
    private Coins coins;
    private String order;
    private Long amount;
    private Long value;

    public BitcoinLogResponseDto(BitcoinLog bitcoinLog){
        this.createdDate = bitcoinLog.getCreatedDate();
        this.coins = bitcoinLog.getCoins();
        this.value = bitcoinLog.getValue();
        this.amount = bitcoinLog.getAmount();

        if(amount<0) {
            amount*=-1;
            this.order = "매도";
        }else {
            this.order = "매수";
        }
    }
}
