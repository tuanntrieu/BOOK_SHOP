package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.request.*;
import com.example.projectbase.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestApiV1
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "API Login")
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        return VsResponseUtil.success(authService.login(request));
    }

    @Operation(summary = "API test")
    @PostMapping("auth/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDto requestDto) {
        return VsResponseUtil.success(authService.refresh(requestDto));
    }

    @Operation(summary = "API Register")
    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return VsResponseUtil.success(authService.register(registerRequestDto));
    }

    @Operation(summary = "API forget password")
    @PostMapping(UrlConstant.Auth.FORGET_PASSWORD)
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequestDto requestDto) {
        return VsResponseUtil.success(authService.forgetPassword(requestDto));
    }

    @Operation(summary = "API change password")
    @PatchMapping(UrlConstant.Auth.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto, @PathVariable String username) {
        return VsResponseUtil.success(authService.changePassword(requestDto, username));


    }

    @Operation(summary = "API Logout")
    @PostMapping(UrlConstant.Auth.LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return VsResponseUtil.success(authService.logout(request, response, authentication));
    }

    @Operation(summary = "API Admin Register")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(UrlConstant.Auth.ADMIN_REGISTER)
    public ResponseEntity<?> adminRegister(@Valid @RequestBody AdminRegisterRequestDto requestDto) {
        return VsResponseUtil.success(authService.adminRegister(requestDto));
    }


}
