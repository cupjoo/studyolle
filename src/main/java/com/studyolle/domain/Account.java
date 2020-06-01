package com.studyolle.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;    // varchar(255)
    private boolean emailVerified;
    private String emailCheckToken;
    private LocalDateTime joinedAt;
    private String bio;
    private String url;
    private String occupation;
    private String location;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private LocalDateTime emailCheckTokenGeneratedAt;
    private boolean studyCreatedByEmail;
    private boolean studyCreatedByWeb;
    private boolean studyEnrollmentResultByEmail;
    private boolean studyEnrollmentResultByWeb;
    private boolean studyUpdatedByEmail;
    private boolean studyUpdatedByWeb;

    @Builder
    public Account(String email, String nickname, String password){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        studyCreatedByWeb = true;
        studyEnrollmentResultByWeb = true;
        studyUpdatedByWeb = true;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }
    public void completeSignUp(){
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }

    public void chanagePersonalInfo(String url, String occupation, String location,
                            String bio, String profileImage){
        this.url = url;
        this.occupation = occupation;
        this.location = location;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeNotifications(boolean studyCreatedByWeb, boolean studyCreatedByEmail,
                                    boolean studyUpdatedByWeb, boolean studyUpdatedByEmail,
                                    boolean studyEnrollmentResultByEmail,
                                    boolean studyEnrollmentResultByWeb) {
        this.studyCreatedByWeb = studyCreatedByWeb;
        this.studyCreatedByEmail = studyCreatedByEmail;
        this.studyUpdatedByWeb = studyUpdatedByWeb;
        this.studyUpdatedByEmail = studyUpdatedByEmail;
        this.studyEnrollmentResultByEmail = studyEnrollmentResultByEmail;
        this.studyEnrollmentResultByWeb = studyEnrollmentResultByWeb;
    }
}
