package com.studyolle.study;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Entity
public class Study {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String path;

    private String title;

    private String shortDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String fullDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String image;
    
    private LocalDateTime publishedDateTime;

    private LocalDateTime closedDateTime;

    private LocalDateTime recruitingUpdatedDateTime;

    private boolean recruiting;

    private boolean published;

    private boolean closed;

    private boolean useBanner;

    @OneToMany(mappedBy = "manager", fetch = LAZY)
    private List<Manager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<Member> members = new ArrayList<>();
}
