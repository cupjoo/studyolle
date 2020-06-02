package com.studyolle.account;

import com.studyolle.domain.Account;
import com.studyolle.settings.form.Notifications;
import com.studyolle.settings.form.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    public Account processNewAccount(SignUpForm signUpForm) {
        Account account = saveNewAccount(signUpForm);
        sendSignUpConfirmEmail(account);
        return account;
    }

    private Account saveNewAccount(@Valid SignUpForm signUpForm) {
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        account.generateEmailCheckToken();
        return accountRepository.save(account);
    }

    public void sendSignUpConfirmEmail(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("스터디올래, 회원가입 인증");
        mailMessage.setText("/check-email-token?token=" + newAccount.getEmailCheckToken()
                + "&email=" + newAccount.getEmail());
        javaMailSender.send(mailMessage);
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),   // principal 객체
                account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(emailOrNickname);
        if(account == null){
            account = Optional.of(accountRepository.findByNickname(emailOrNickname))
                    .orElseThrow(() -> new UsernameNotFoundException(emailOrNickname));
        }
        return new UserAccount(account);
    }

    public void completeSignUp(Account account) {
        account.completeSignUp();
        login(account);
    }

    public void updateProfile(Account account, Profile profile) {
        Account updateAccount = accountRepository.findByNickname(account.getNickname());
        updateAccount.chanagePersonalInfo(profile.getUrl(),
                profile.getOccupation(), profile.getLocation(),
                profile.getBio(), profile.getProfileImage());
    }

    public void updatePassword(Account account, String newPassword) {
        Account updateAccount = accountRepository.findByNickname(account.getNickname());
        updateAccount.changePassword(passwordEncoder.encode(newPassword));
    }

    public void updateNotifications(Account account, Notifications notifications) {
        Account updateAccount = accountRepository.findByNickname(account.getNickname());
        updateAccount.changeNotifications(notifications.isStudyCreatedByWeb(),
                notifications.isStudyCreatedByEmail(),
                notifications.isStudyUpdatedByWeb(),
                notifications.isStudyUpdatedByEmail(),
                notifications.isStudyEnrollmentResultByEmail(),
                notifications.isStudyEnrollmentResultByWeb());
    }

    public void updateNickname(Account account, String nickname) {
        Account updateAccount = accountRepository.findByNickname(account.getNickname());
        updateAccount.changeNickname(nickname);
        login(updateAccount);
    }

    public void sendLoginLink(Account account) {
        account.generateEmailCheckToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(account.getEmail());
        mailMessage.setSubject("스터디올래, 로그인 링크");
        mailMessage.setText("/login-by-email?token=" + account.getEmailCheckToken() +
                "&email=" + account.getEmail());
        javaMailSender.send(mailMessage);
    }
}
