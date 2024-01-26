package com.soogoori.boardserver.controller;

import com.soogoori.boardserver.aop.LoginCheck;
import com.soogoori.boardserver.dto.UserDto;
import com.soogoori.boardserver.dto.request.UserDeleteId;
import com.soogoori.boardserver.dto.request.UserLoginRequest;
import com.soogoori.boardserver.dto.request.UserUpdatePasswordRequest;
import com.soogoori.boardserver.dto.response.LoginResponse;
import com.soogoori.boardserver.dto.response.UserInfoResponse;
import com.soogoori.boardserver.service.impl.UserServiceImpl;
import com.soogoori.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserServiceImpl userService;
    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE =
            new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
    private static LoginResponse loginResponse;
    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDto userDto) {
        if (UserDto.hasNullDataBeforeSignup(userDto)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDto);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest loginRequest,
                            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        UserDto userInfo = userService.login(id, password);

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.getStatus() == (UserDto.Status.ADMIN))
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id);

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지워진 유저 정보입니다.");
        }

        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null) id = SessionUtil.getLoginAdminId(session);
        UserDto memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                                            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = SessionUtil.getLoginMemberId(session);
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String newPassword = userUpdatePasswordRequest.getNewPassword();

        try {
            userService.updatePassword(Id, beforePassword, newPassword);
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
                                                  HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(Id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.info("deleteID 실패");
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }
}
