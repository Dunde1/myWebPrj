package org.me.springboot.domain.bitcoin;

import org.me.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BitcoinLogRepository extends JpaRepository<BitcoinLog, Long> {
    List<BitcoinLog> findByUser(User user);
}
