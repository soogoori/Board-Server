package com.soogoori.boardserver.service;

import com.soogoori.boardserver.dto.UserDto;

public interface UserService {

    void register(UserDto userProfile);

    UserDto login(String userId, String password);

    boolean isDuplicatedId(String userId);

    UserDto getUserInfo(String userId);

    void updatePassword(String userId, String beforePassword, String newPassword);

    void deleteId(String userId, String password);
}
