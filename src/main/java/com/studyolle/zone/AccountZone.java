package com.studyolle.zone;

import com.studyolle.account.Account;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class AccountZone {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Builder
    public AccountZone(Account account, Zone zone){
        this.account = account;
        this.zone = zone;
        addZone();
    }

    private void addZone(){
        zone.getAccountZones().add(this);
    }
}
