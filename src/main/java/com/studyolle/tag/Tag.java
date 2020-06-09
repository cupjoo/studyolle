package com.studyolle.tag;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

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

    @OneToMany(mappedBy = "tag", fetch = LAZY)
    private List<AccountTag> accountTags = new ArrayList<>();

    @OneToMany(mappedBy = "study", fetch = LAZY)
    private List<StudyTag> studyTags = new ArrayList<>();

    @Builder
    public Tag(String title){
        this.title = title;
    }
}
