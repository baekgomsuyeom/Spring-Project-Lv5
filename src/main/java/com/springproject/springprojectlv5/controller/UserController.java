package com.springproject.springprojectlv5.controller;

import com.springproject.springprojectlv5.dto.LoginRequestDto;
import com.springproject.springprojectlv5.dto.MsgResponseDto;
import com.springproject.springprojectlv5.dto.SignOutRequestDto;
import com.springproject.springprojectlv5.dto.SignupRequestDto;
import com.springproject.springprojectlv5.security.UserDetailsImpl;
import com.springproject.springprojectlv5.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new MsgResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new MsgResponseDto("로그인 완료", HttpStatus.OK.value()));
    }

    // 회원 탈퇴
    @DeleteMapping("/signOut")
    private ResponseEntity<MsgResponseDto> signOut(@RequestBody SignOutRequestDto signOutRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.signOut(signOutRequestDto, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("회원탈퇴 완료", HttpStatus.OK.value()));
    }
}
