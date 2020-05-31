package com.studyolle.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class AccountTag {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public AccountTag(Account account, Tag tag){
       this.account = account;
       this.tag = tag;
       addTag();
    }

    private void addTag(){
        account.getAccountTags().add(this);
        tag.getAccountTags().add(this);
    }
}
