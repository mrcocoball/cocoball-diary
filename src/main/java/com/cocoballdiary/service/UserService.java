package com.cocoballdiary.service;

import com.cocoballdiary.domain.User;
import com.cocoballdiary.domain.constrant.UserRole;
import com.cocoballdiary.dto.UserJoinDto;
import com.cocoballdiary.exception.DiaryException;
import com.cocoballdiary.exception.ErrorCode;
import com.cocoballdiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void join(UserJoinDto userJoinDto) throws DiaryException {

        String uid = userJoinDto.getUid();

        boolean exist = userRepository.existsById(uid);

        if(exist) {
            throw new DiaryException(ErrorCode.USER_EXISTS);
        }

        User user = userJoinDto.toEntity();
        user.changePassword(passwordEncoder.encode(userJoinDto.getPassword()));
        user.addRole(UserRole.USER);

        userRepository.save(user);
    }


}
