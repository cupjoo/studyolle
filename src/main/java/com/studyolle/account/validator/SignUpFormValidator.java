package com.studyolle.account.validator;

import com.studyolle.account.AccountRepository;
import com.studyolle.account.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 커스텀 검증
@RequiredArgsConstructor
@Component
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;
        if(accountRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email", "invaild.email",
                    new Object[]{signUpForm.getEmail()}, "이미 사용 중인 이메일입니다.");
        }

        if(accountRepository.existsByNickname(signUpForm.getNickname())){
            errors.rejectValue("nickname", "invaild.nickname",
                    new Object[]{signUpForm.getNickname()}, "이미 사용 중인 닉네임입니다.");
        }
    }
}
