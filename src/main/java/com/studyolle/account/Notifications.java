package com.studyolle.account;

import lombok.*;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class Notifications {

    private boolean studyCreatedByEmail;
    private boolean studyCreatedByWeb;
    private boolean studyEnrollmentResultByEmail;
    private boolean studyEnrollmentResultByWeb;
    private boolean studyUpdatedByEmail;
    private boolean studyUpdatedByWeb;

    public Notifications() {
        studyCreatedByWeb = true;
        studyEnrollmentResultByWeb = true;
        studyUpdatedByWeb = true;
    }

    public void changeNotifications(Notifications notifications){
        this.studyCreatedByWeb = notifications.isStudyCreatedByWeb();
        this.studyCreatedByEmail = notifications.isStudyCreatedByEmail();
        this.studyUpdatedByWeb = notifications.isStudyUpdatedByWeb();
        this.studyUpdatedByEmail = notifications.isStudyUpdatedByEmail();
        this.studyEnrollmentResultByEmail = notifications.isStudyEnrollmentResultByEmail();
        this.studyEnrollmentResultByWeb = notifications.isStudyEnrollmentResultByWeb();
    }
}
