package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.function.CoinService;
import org.me.springboot.web.dto.bitcoin.CoinInfoResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class BitcoinApiController {

    private final CoinService coinService;

    @ResponseBody
    @PostMapping("/bitcoin/api/coinInfo")
    public CoinInfoResponseDto coinInfo(){
        return coinService.getCoinInfos();
    }

}
