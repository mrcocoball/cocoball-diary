package com.cocoballdiary.dto.security;

import com.cocoballdiary.domain.User;
import com.cocoballdiary.dto.security.dto.UserSecurityDto;
import com.cocoballdiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = userRepository.getWithRoles(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다");
        }

        User user = result.get();

        UserSecurityDto userSecurityDto =
                new UserSecurityDto(
                        user.getUid(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getIntroduce(),
                        false,
                        user.isDeleted(),
                        user.getRoleSet()
                                .stream().map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                                .collect(Collectors.toList())
                );

        return userSecurityDto;
    }

}
