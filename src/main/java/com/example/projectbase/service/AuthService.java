package com.example.projectbase.service;

import com.example.projectbase.domain.dto.request.*;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.LoginResponseDto;
import com.example.projectbase.domain.dto.response.TokenRefreshResponseDto;
import com.example.projectbase.domain.entity.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto request);

  TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

  CommonResponseDto logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

  User register(RegisterRequestDto newUser);

  CommonResponseDto forgetPassword(ForgetPasswordRequestDto requestDto);

  CommonResponseDto changPassword(ChangePasswordRequestDto requestDto,String username);



}
