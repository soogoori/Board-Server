package com.soogoori.boardserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDto {

    public enum Status{
        DEFAULT, DELETED, ADMIN
    }

    private int id;
    private String userId;
    private String password;
    private String nickName;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw;
    private Status status;
    private Date updateTime;

    public static boolean hasNullDataBeforeSignup(UserDto userDto) {
        return userDto.getUserId() == null || userDto.getPassword() == null
                || userDto.getNickName() == null;
    }
}
