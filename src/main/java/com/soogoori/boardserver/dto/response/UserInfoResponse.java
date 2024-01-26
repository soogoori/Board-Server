package com.soogoori.boardserver.dto.response;

import com.soogoori.boardserver.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private UserDto userDto;
}
