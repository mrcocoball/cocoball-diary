package com.cocoballdiary.dto.security;

import com.cocoballdiary.domain.User;
import com.cocoballdiary.domain.constrant.UserRole;
import com.cocoballdiary.dto.security.dto.UserSecurityDto;
import com.cocoballdiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }

        return generateDto(email, paramMap);
    }

    private UserSecurityDto generateDto(String email, Map<String, Object> params) {

        Optional<User> result = userRepository.findByEmail(email);

        // DB에 해당 이메일 사용자가 없을 경우
        if(result.isEmpty()) {

            // 회원 추가
            User user = User.of(
                    email,
                    passwordEncoder.encode("1111"),
                    email,
                    "",
                    false,
                    true
            );
            user.addRole(UserRole.USER);
            userRepository.save(user);

            // UserSecurityDto 구성 및 반환

            UserSecurityDto userSecurityDto =
                    new UserSecurityDto(email, "1111", email, "",false, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            userSecurityDto.setProps(params);

            return userSecurityDto;

        } else {

            User user = result.get();
            UserSecurityDto userSecurityDto =
                    new UserSecurityDto(
                            user.getUid(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getIntroduce(),
                            user.isDeleted(),
                            user.isSocial(),
                            user.getRoleSet()
                                    .stream().map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                                    .collect(Collectors.toList())
                    );

            return userSecurityDto;
        }
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {

        Object value = paramMap.get("kakao_account");

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String email = (String)accountMap.get("email");

        return email;

    }

}
