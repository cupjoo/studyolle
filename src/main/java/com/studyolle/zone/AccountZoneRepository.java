package com.studyolle.zone;

import com.studyolle.domain.Account;
import com.studyolle.domain.AccountZone;
import com.studyolle.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountZoneRepository extends JpaRepository<AccountZone, Long> {

    Optional<AccountZone> findAccountZoneByAccountAndZone(Account account, Zone zone);

    List<AccountZone> findAccountZoneByAccount(Account account);
}