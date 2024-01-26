package com.soogoori.boardserver.service.impl;

import com.soogoori.boardserver.dto.UserDto;
import com.soogoori.boardserver.exception.DuplicateIdException;
import com.soogoori.boardserver.mapper.UserProfileMapper;
import com.soogoori.boardserver.service.UserService;
import com.soogoori.boardserver.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserProfileMapper userProfileMapper;
    @Override
    public void register(UserDto userDto) {
        boolean duplIdResult = isDuplicatedId(userDto.getUserId());
        if (duplIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        userDto.setCreateTime(new Date());
        userDto.setPassword(SHA256Util.encryptSHA256(userDto.getPassword()));
        int insertCount = userProfileMapper.register(userDto);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userDto);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDto);
        }
    }

    @Override
    public boolean isDuplicatedId(String userId) {
        return userProfileMapper.idCheck(userId)==1;
    }

    @Override
    public UserDto login(String userId, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(userId, cryptoPassword);
        return memberInfo;
    }


    @Override
    public UserDto getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String userId, String beforePassword, String newPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(userId, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(newPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new IllegalArgumentException("updatePassword ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    @Override
    public void deleteId(String userId, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userProfileMapper.findByIdAndPassword(userId, cryptoPassword);

        if (memberInfo != null) {
            userProfileMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }
}
