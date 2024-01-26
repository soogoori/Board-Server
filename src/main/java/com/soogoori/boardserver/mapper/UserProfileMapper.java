package com.soogoori.boardserver.mapper;

import com.soogoori.boardserver.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {
    public UserDto getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("phone") String phone, @Param("address") String address);

    int updateUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name, @Param("phone") String phone, @Param("address") String address);

    int deleteUserProfile(@Param("id") String id);

    public int register(UserDto userDto);

    public UserDto findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(String id);

    public int updatePassword(UserDto userDTO);

    public int updateAddress(UserDto userDTO);
}
