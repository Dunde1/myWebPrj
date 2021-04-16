package org.me.springboot.service.function.bitcoin;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.domain.bitcoin.BitcoinLog;
import org.me.springboot.domain.bitcoin.BitcoinLogRepository;
import org.me.springboot.domain.wallet.Wallet;
import org.me.springboot.domain.wallet.WalletRepository;
import org.me.springboot.domain.user.User;
import org.me.springboot.domain.user.UserRepository;
import org.me.springboot.web.dto.bitcoin.BitcoinDealrequestDto;
import org.me.springboot.web.dto.bitcoin.BitcoinLogResponseDto;
import org.me.springboot.web.dto.bitcoin.CoinInfoResponseDto;
import org.me.springboot.web.dto.bitcoin.CoinInfosWebClientDto;
import org.me.springboot.web.dto.wallet.WalletResponseDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
@Service
public class BitcoinService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BitcoinLogRepository bitcoinLogRepository;
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

    @Transactional
    public WalletResponseDto walletCreate(SessionUser suser){
        Optional<User> ouser = userRepository.findByEmail(suser.getEmail());
        User user = ouser.get();
        Optional<Wallet> owallet = walletRepository.findByUser(user);
        if(owallet.isPresent()) {
            return new WalletResponseDto(owallet.get());
        }else {
            WalletResponseDto walletResponseDto = WalletResponseDto.builder()
                    .user(user)
                    .cash(100000000l).loan(100000000l)
                    .btc(0l).bch(0l).btg(0l).eos(0l).etc(0l).eth(0l).ltc(0l).xrp(0l).build();
            walletRepository.save(walletResponseDto.toEntity());
            return walletResponseDto;
        }
    }

    @Transactional
    public List<BitcoinLogResponseDto> bitcoinLogGetAll(String email){
        Optional<User> ouser = userRepository.findByEmail(email);
        User user = ouser.get();
        return bitcoinLogRepository.findByUser(user).stream()
                .map(BitcoinLogResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void bitcoinDealUpdate(BitcoinDealrequestDto bitcoinDealrequestDto){
        User user = userRepository.findByEmail(bitcoinDealrequestDto.getUserEmail()).get();
        Wallet wallet = walletRepository.findByUser(user).get();

        switch (bitcoinDealrequestDto)


        bitcoinLogRepository.save(BitcoinLog.builder().user(user).coins(co))
    }
}
