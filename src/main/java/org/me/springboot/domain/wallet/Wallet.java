package org.me.springboot.domain.wallet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.me.springboot.domain.BaseTimeEntity;
import org.me.springboot.domain.user.User;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Wallet extends BaseTimeEntity{
    /** Wallet 엔티티
     *
     * id : 지갑 ID [기본키, AUTO_INCREMENT]
     * user : 유저 ID [외래키], 한 유저당 하나의 지갑만 보유
     * cash : 유저의 현금 정보
     * btc, bch, btg, eos, etc, eth, ltc, xrp : 각 코인의 보유현황
     * loan : 유저의 대출금 정보, 현재 대출 갚기 미구현
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId")
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
    public Wallet(User user, Long cash, Long btc, Long bch, Long btg, Long eos, Long etc, Long eth, Long ltc, Long xrp, Long loan){
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
}
