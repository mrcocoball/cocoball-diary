package com.cocoballdiary.service;

import com.cocoballdiary.domain.User;
import com.cocoballdiary.domain.constrant.UserRole;
import com.cocoballdiary.dto.UserDto;
import com.cocoballdiary.dto.UserJoinDto;
import com.cocoballdiary.dto.UserModifyDto;
import com.cocoballdiary.exception.DiaryException;
import com.cocoballdiary.exception.ErrorCode;
import com.cocoballdiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
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

    public UserModifyDto get(String uid) {

        User user = userRepository.findByUid(uid).orElseThrow(() -> new DiaryException(ErrorCode.USER_NOT_FOUND));

        return UserModifyDto.from(user);

    }

    public void modify(UserModifyDto userModifyDto) throws DiaryException {

        User user = userRepository.findByUid(userModifyDto.getUid()).orElseThrow(() -> new DiaryException(ErrorCode.USER_NOT_FOUND));
        user.changeEmail(userModifyDto.getEmail());
        user.changeIntroduce(userModifyDto.getIntroduce());
        String encodePassword = passwordEncoder.encode(userModifyDto.getPassword());
        user.changePassword(encodePassword);

        log.info(user);

        userRepository.save(user);

    }


}
