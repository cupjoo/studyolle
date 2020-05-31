package com.studyolle.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @OneToMany(mappedBy = "tag")
    private List<AccountTag> accountTags = new ArrayList<>();

    @Builder
    public Tag(String title){
        this.title = title;
    }

    public void addTag(AccountTag accountTag){
        accountTags.add(accountTag);
    }
}
