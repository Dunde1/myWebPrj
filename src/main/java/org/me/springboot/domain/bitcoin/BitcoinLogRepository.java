package org.me.springboot.domain.bitcoin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BitcoinLogRepository extends JpaRepository<BitcoinLog, Long> {
}
