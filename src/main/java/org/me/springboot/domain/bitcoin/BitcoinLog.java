package org.me.springboot.domain.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.me.springboot.domain.BaseTimeEntity;
import org.me.springboot.domain.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BitcoinLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId")
    private User user;

    private Coins coins;

    private Long amount;

    private Long value;

    @Builder
    public BitcoinLog(User user, Coins coins, Long amount, Long value){
        this.user = user;
        this.coins = coins;
        this.amount = amount;
        this.value = value;
    }

}
