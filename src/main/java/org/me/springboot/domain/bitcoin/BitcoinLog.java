package org.me.springboot.domain.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.me.springboot.domain.BaseTimeEntity;
import org.me.springboot.domain.user.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BitcoinLog extends BaseTimeEntity{
    /** BitcoinLog 엔티티
     *
     * id : 비트코인 로그 ID [기본키, AUTO_INCREMENT]
     * user : 유저 ID [외래키]
     * coins : 코인의 이름 (btc, bch, btg, eos, etc, eth, ltc, xrp 중 1)
     * amount : 코인의 변화량 (음수 : 판매, 양수 : 구매)
     * value : 현재 코인의 시세
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Coins coins;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long value;

    @Builder
    public BitcoinLog(User user, Coins coins, Long amount, Long value){
        this.user = user;
        this.coins = coins;
        this.amount = amount;
        this.value = value;
    }

}
