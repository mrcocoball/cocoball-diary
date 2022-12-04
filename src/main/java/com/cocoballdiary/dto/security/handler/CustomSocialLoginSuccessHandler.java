package com.cocoballdiary.dto.security.handler;

import com.cocoballdiary.dto.security.dto.UserSecurityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("CustomLoginSuccessHandler onAuthenticationSuccess...");
        log.info(authentication.getPrincipal());

        UserSecurityDto userSecurityDto = (UserSecurityDto) authentication.getPrincipal();

        String encodePw = userSecurityDto.getPassword();

        /* TODO: 추후 회원 정보 수정 페이지 제작 시 활성화

        // 소셜 로그인, 회원의 패스워드 1111
        if (userSecurityDto.isSocial()
                && (userSecurityDto.getPassword().equals("1111")
                || passwordEncoder.matches("1111", userSecurityDto.getPassword())
        )) {
            log.info("비밀번호를 변경해야 합니다.");

            log.info("회원 수정 페이지로 리다이렉션");
            response.sendRedirect("/user/modify");

            return;
        } else {

            response.sendRedirect("/diary/list");

        }
        */

        response.sendRedirect("/diary/list");

    }
}