package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BitcoinDealrequestDto {
    private String userEmail;
    private WalletUpdateRequestDto walletUpdateRequestDto;
    private BitcoinLogUpdateRequestDto bitcoinLogUpdateRequestDto;

    @Builder
    public BitcoinDealrequestDto(String userEmail,
                                 WalletUpdateRequestDto walletUpdateRequestDto,
                                 BitcoinLogUpdateRequestDto bitcoinLogUpdateRequestDto){
        this.userEmail = userEmail;
        this.walletUpdateRequestDto = walletUpdateRequestDto;
        this.bitcoinLogUpdateRequestDto = bitcoinLogUpdateRequestDto;
    }
}
