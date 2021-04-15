package org.me.springboot.web.dto.wallet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.wallet.Wallet;
import org.me.springboot.domain.user.User;

@Getter
@NoArgsConstructor
public class WalletResponseDto {
    private User user;
    private Long cash;
    private Long btc;
    private Long bch;
    private Long btg;
    private Long eos;
    private Long etc;
    private Long eth;
    private Long ltc;
    private Long xrp;
    private Long loan;

    @Builder
    public WalletResponseDto(User user, Long cash, Long btc, Long bch, Long btg, Long eos, Long etc, Long eth, Long ltc, Long xrp, Long loan){
        this.user = user;
        this.cash = cash;
        this.btc = btc;
        this.bch = bch;
        this.btg = btg;
        this.eos = eos;
        this.etc = etc;
        this.eth = eth;
        this.ltc = ltc;
        this.xrp = xrp;
        this.loan = loan;
    }

    public WalletResponseDto(Wallet wallet){
        this.user = wallet.getUser();
        this.cash = wallet.getCash();
        this.btc = wallet.getBtc();
        this.bch = wallet.getBch();
        this.btg = wallet.getBtg();
        this.eos = wallet.getEos();
        this.etc = wallet.getEtc();
        this.eth = wallet.getEth();
        this.ltc = wallet.getLtc();
        this.xrp = wallet.getXrp();
        this.loan = wallet.getLoan();
    }

    public Wallet toEntity(){
        return Wallet.builder().user(this.user).cash(this.cash)
                .btc(this.btc).bch(this.bch).btg(this.btg)
                .eos(this.eos).etc(this.etc).eth(this.eth)
                .ltc(this.ltc).xrp(this.xrp).loan(this.loan).build();
    }
}
