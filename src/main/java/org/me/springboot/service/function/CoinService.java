package org.me.springboot.service.function;

import lombok.RequiredArgsConstructor;
import org.me.springboot.web.dto.bitcoin.CoinInfoResponseDto;
import org.me.springboot.web.dto.bitcoin.CoinInfosWebClientDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
@Service
public class CoinService {

    private final Environment environment;

    public CoinInfoResponseDto getCoinInfos(){
        WebClient webClient = WebClient.builder().build();
        String publicKey = environment.getProperty("publicKey");

        Flux<CoinInfosWebClientDto> btcMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/btckrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> bchMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/bchkrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> btgMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/btgkrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> eosMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/eoskrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> etcMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/etckrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> ethMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/ethkrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> ltcMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/ltckrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> xrpMono = webClient.get().uri("https://api.cryptowat.ch/markets/bithumb/xrpkrw/price?apikey="+publicKey).retrieve().bodyToFlux(CoinInfosWebClientDto.class);

        return CoinInfoResponseDto.builder().btcPrice(btcMono.blockFirst().getResult().get("price"))
                .bchPrice(bchMono.blockFirst().getResult().get("price"))
                .btgPrice(btgMono.blockFirst().getResult().get("price"))
                .eosPrice(eosMono.blockFirst().getResult().get("price"))
                .etcPrice(etcMono.blockFirst().getResult().get("price"))
                .ethPrice(ethMono.blockFirst().getResult().get("price"))
                .ltcPrice(ltcMono.blockFirst().getResult().get("price"))
                .xrpPrice(xrpMono.blockFirst().getResult().get("price"))
                .build();
    }
}
