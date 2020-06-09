package com.studyolle.tag;

import com.studyolle.account.Account;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class AccountTag {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public AccountTag(Account account, Tag tag){
       this.account = account;
       this.tag = tag;
       addTagToAccount();
    }

    private void addTagToAccount(){
        tag.getAccountTags().add(this);
    }

    public String getTitle(){
        return tag.getTitle();
    }
}
