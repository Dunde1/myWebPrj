package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.function.bitcoin.BitcoinService;
import org.me.springboot.web.dto.bitcoin.BitcoinDealrequestDto;
import org.me.springboot.web.dto.bitcoin.BitcoinLogResponseDto;
import org.me.springboot.web.dto.bitcoin.CoinInfoResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BitcoinApiController {
    /** 비트코인의 정보요청, 거래내역 요청, 거래요청을 담당하는 컨트롤러입니다.
     *
     * coinInfo() : 비트코인의 정보를 요청하고 결과값을 받아옵니다.
     * bitcoinLog() : 해당 이메일을 사용하는 사용자의 비트코인 거래내역을 전부 리스트로 받아옵니다.
     * walletUpdate() : 해당 유저의 지갑(wallet) 정보를 수정합니다.
     */
    private final BitcoinService bitcoinService;

    @PostMapping("/bitcoin/api/coinInfo")
    public CoinInfoResponseDto coinInfo(){
        return bitcoinService.getCoinInfos();
    }

    @PostMapping("/bitcoin/api/bitcoinLog/{email}")
    public List<BitcoinLogResponseDto> bitcoinLog(@PathVariable("email") String email) {
        return bitcoinService.bitcoinLogGetAll(email);
    }

    @PostMapping("/bitcoin/api/bitcoinDealUpdate")
    public Long walletUpdate(@RequestBody BitcoinDealrequestDto bitcoinDealrequestDto) {
        return bitcoinService.bitcoinDealUpdate(bitcoinDealrequestDto);
    }

}
