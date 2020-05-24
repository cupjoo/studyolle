package com.studyolle.settings.validator;

import com.studyolle.settings.form.PasswordForm;
import org.springframework.validation.*;

public class PasswordFormValidator implements Validator {

    // 사용하는 빈이 없으므로 빈으로 등록 X

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordForm passwordForm = (PasswordForm) target;
        if(!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordConfirm())){
            errors.rejectValue("newPassword", "wrong.value",
                    "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }
}
