package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.function.bitcoin.BitcoinService;
import org.me.springboot.web.dto.bitcoin.BitcoinDealrequestDto;
import org.me.springboot.web.dto.bitcoin.BitcoinLogResponseDto;
import org.me.springboot.web.dto.bitcoin.CoinInfoResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BitcoinApiController {

    private final BitcoinService bitcoinService;

    @ResponseBody
    @PostMapping("/bitcoin/api/coinInfo")
    public CoinInfoResponseDto coinInfo(){
        return bitcoinService.getCoinInfos();
    }

    @ResponseBody
    @PostMapping("/bitcoin/api/bitcoinLog/{email}")
    public List<BitcoinLogResponseDto> bitcoinLog(@PathVariable("email") String email) {
        return bitcoinService.bitcoinLogGetAll(email);
    }

    @PostMapping("/bitcoin/api/bitcoinDealUpdate")
    public void walletUpdate(@RequestBody BitcoinDealrequestDto bitcoinDealrequestDto) {

    }

}
