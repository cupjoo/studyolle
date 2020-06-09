package com.studyolle.study;

import com.studyolle.account.Account;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Entity
public class Manager {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Manager(Account account, Study study){
        this.account = account;
        this.study = study;
        addManagerToStudy();
    }

    private void addManagerToStudy(){
        study.getManagers().add(this);
    }
}
