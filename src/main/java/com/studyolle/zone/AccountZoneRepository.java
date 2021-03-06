package com.studyolle.zone;

import com.studyolle.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AccountZoneRepository extends JpaRepository<AccountZone, Long> {

    Optional<AccountZone> findAccountZoneByAccountAndZone(Account account, Zone zone);

    List<AccountZone> findAccountZoneByAccount(Account account);
}
