package com.jangho.latters.security.adapter.in.dto;

import com.jangho.latters.common.model.enums.ResponseCode;
import com.jangho.latters.security.domain.exception.SecurityException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class LoginDto {
        private String email;
        private String password;

        public void validate() {
                if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
                        throw new SecurityException(ResponseCode.FAIL, "invalid request body");
                }

                this.email = email.toLowerCase().trim();
        }
}
