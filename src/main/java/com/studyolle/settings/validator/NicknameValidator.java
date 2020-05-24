package com.studyolle.settings.validator;

import com.studyolle.account.AccountRepository;
import com.studyolle.domain.Account;
import com.studyolle.settings.form.NicknameForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@RequiredArgsConstructor
@Component
public class NicknameValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NicknameValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NicknameForm nicknameForm = (NicknameForm) target;
        Account byNickname = accountRepository.findByNickname(nicknameForm.getNickname());
        if(byNickname != null){
            errors.rejectValue("nickname", "wrong.value",
                    "입력하신 닉네임을 사용할 수 없습니다.");
        }
    }
}
