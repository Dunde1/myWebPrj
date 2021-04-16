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
public class BitcoinLog{

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

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public BitcoinLog(User user, Coins coins, Long amount, Long value){
        this.user = user;
        this.coins = coins;
        this.amount = amount;
        this.value = value;
    }

}
