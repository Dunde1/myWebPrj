package org.me.springboot.service.function.bitcoin;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.domain.bitcoin.BitcoinLog;
import org.me.springboot.domain.bitcoin.BitcoinLogRepository;
import org.me.springboot.domain.bitcoin.Coins;
import org.me.springboot.domain.wallet.Wallet;
import org.me.springboot.domain.wallet.WalletRepository;
import org.me.springboot.domain.user.User;
import org.me.springboot.domain.user.UserRepository;
import org.me.springboot.web.dto.bitcoin.*;
import org.me.springboot.web.dto.wallet.WalletResponseDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
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
    /** 비트코인의 정보가져오기, 지갑 생성, 로그 리스트 가져오기, 거래내역 업데이트를 담당하는 서비스 입니다.
     *
     * coinInfoResponseDto : 비트코인(btc, bch, btg, eos, etc, eth, ltc, xrp)의 현재 가격정보를 담는 Dto입니다.
     *
     * getCoinInfoUsedApi() : 매 1초마다 외부 실제 비트코인 서버에 api를 요청하여 각 코인들의 현재 가격정보를 가져와 Dto에 저장합니다.
     * getCoinInfos() : 저장된 현재 코인의 가격정보 Dto를 전달합니다.
     * walletCreate() : 지갑정보가 없는 유저의 초기 지갑정보를 저장합니다.
     * bitcoinLogGetAll() : 해당 이메일을 가진 유저의 비트코인 거래내역을 전부 리스트에 담아 전달합니다.
     * bitcoinDealUpdate() : 수정된 지갑을 저장하고 해당 변화량을 거래내역에 저장합니다.
     */
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BitcoinLogRepository bitcoinLogRepository;

    private CoinInfoResponseDto coinInfoResponseDto;

    @Scheduled(fixedDelay = 1000L)
    public void getCoinInfoUsedApi(){
        WebClient webClient = WebClient.builder().build();

        Flux<CoinInfosWebClientDto> btcMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/BTC_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> bchMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/BCH_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> btgMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/BTG_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> eosMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/EOS_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> etcMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/ETC_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> ethMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/ETH_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> ltcMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/LTC_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);
        Flux<CoinInfosWebClientDto> xrpMono = webClient.get().uri("https://api.bithumb.com/public/transaction_history/XRP_KRW").retrieve().bodyToFlux(CoinInfosWebClientDto.class);

        coinInfoResponseDto = CoinInfoResponseDto.builder().btcPrice(btcMono.blockFirst().getPrice())
                .bchPrice(bchMono.blockFirst().getPrice())
                .btgPrice(btgMono.blockFirst().getPrice())
                .eosPrice(eosMono.blockFirst().getPrice())
                .etcPrice(etcMono.blockFirst().getPrice())
                .ethPrice(ethMono.blockFirst().getPrice())
                .ltcPrice(ltcMono.blockFirst().getPrice())
                .xrpPrice(xrpMono.blockFirst().getPrice())
                .build();
    }

    public CoinInfoResponseDto getCoinInfos(){
        return coinInfoResponseDto;
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
    public Long bitcoinDealUpdate(BitcoinDealrequestDto bitcoinDealrequestDto){
        User user = userRepository.findByEmail(bitcoinDealrequestDto.getUserEmail()).orElseThrow(() ->
                new IllegalArgumentException("User를 찾을 수 없습니다."));
        Wallet wallet = walletRepository.findByUser(user).orElseThrow(() ->
                new IllegalArgumentException("wallet을 찾을 수 없습니다."));

        Long amount = bitcoinDealrequestDto.getCoinAmount();

        //wallet 업데이트
        switch (bitcoinDealrequestDto.getCoins()){
            case btc:   wallet.setBtc(amount);
                break;
            case bch:   wallet.setBch(amount);
                break;
            case btg:   wallet.setBtg(amount);
                break;
            case eos:   wallet.setEos(amount);
                break;
            case etc:   wallet.setEtc(amount);
                break;
            case eth:   wallet.setEth(amount);
                break;
            case ltc:   wallet.setLtc(amount);
                break;
            case xrp:   wallet.setXrp(amount);
                break;
        }
        wallet.setCash(bitcoinDealrequestDto.getCash());

        //bitcoinLog 저장
        Coins coins = bitcoinDealrequestDto.getCoins();
        amount = bitcoinDealrequestDto.getAmount();
        Long value = bitcoinDealrequestDto.getValue();

        return bitcoinLogRepository.save(BitcoinLog.builder()
                .user(user).coins(coins).amount(amount).value(value).build()).getId();
    }
}
