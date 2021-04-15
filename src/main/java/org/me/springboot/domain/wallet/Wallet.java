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
