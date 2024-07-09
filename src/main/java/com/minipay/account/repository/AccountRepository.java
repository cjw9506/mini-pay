package com.minipay.account.repository;

import com.minipay.account.domain.Account;
import com.minipay.account.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUserId(Long userId);

    Account findByUserIdAndType(Long userId, Type type);
}
