package com.studyolle.tag;

import com.studyolle.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AccountTagRepository extends JpaRepository<AccountTag, Long> {

    @Query("select at from AccountTag at where at.account = :account and at.tag = :tag")
    AccountTag findAccountTag(@Param("account") Account account, @Param("tag") Tag tag);

    List<AccountTag> findByAccount(Account account);
}
