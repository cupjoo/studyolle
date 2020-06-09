package com.studyolle.tag;

import com.studyolle.study.Study;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class StudyTag {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public StudyTag(Study study, Tag tag){
        this.study = study;
        this.tag = tag;
        addTagToStudy();
    }

    private void addTagToStudy(){
        tag.getStudyTags().add(this);
    }
}
