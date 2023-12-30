package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.RoleConstant;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.CartDto;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.dto.common.DataMailDto;
import com.example.projectbase.domain.dto.request.*;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.LoginResponseDto;
import com.example.projectbase.domain.dto.response.TokenRefreshResponseDto;
import com.example.projectbase.domain.entity.User;
import com.example.projectbase.domain.mapper.UserMapper;
import com.example.projectbase.exception.DataIntegrityViolationException;
import com.example.projectbase.exception.InvalidException;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.exception.UnauthorizedException;
import com.example.projectbase.repository.RoleRepository;
import com.example.projectbase.repository.UserRepository;
import com.example.projectbase.security.UserPrincipal;
import com.example.projectbase.security.jwt.JwtTokenProvider;
import com.example.projectbase.service.AuthService;
import com.example.projectbase.service.CartService;
import com.example.projectbase.service.CustomerService;
import com.example.projectbase.util.RandomPasswordUtil;
import com.example.projectbase.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final SendMailUtil sendMailUtil;

    private final RoleRepository roleRepository;

    private final CustomerService customerService;

    private final CartService cartService;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmailOrUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);
            Optional<User> user = userRepository.fidByUsernameOrEmail(request.getEmailOrUsername(), request.getEmailOrUsername());
            if (user.isEmpty()) {
                throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                        new String[]{request.getEmailOrUsername()});
            }
            user.get().setAccessToken(accessToken);
            user.get().setRefreshToken(refreshToken);
            userRepository.save(user.get());
            return new LoginResponseDto(accessToken, refreshToken, userPrincipal.getId(), userPrincipal.getUsername(), authentication.getAuthorities());
        } catch (InternalAuthenticationServiceException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
        }
    }

    @Override
    public TokenRefreshResponseDto refresh(TokenRefreshRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidException(ErrorMessage.Auth.INVALID_REFRESH_TOKEN);
        }
        String username = jwtTokenProvider.extractClaimUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                        new String[]{username}));
        // Check if the provided refresh token matches the one stored in the database
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new InvalidException(ErrorMessage.Auth.INVALID_REFRESH_TOKEN);
        }
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        // Generate a new access token
        String newAccessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
        // Generate a new refresh token
        String newRefreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);

        // Update the user's refresh token in the database
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);
        return new TokenRefreshResponseDto(newAccessToken, newRefreshToken);
    }

    @Override
    public CommonResponseDto logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextLogoutHandler logout = new SecurityContextLogoutHandler();
        logout.logout(request, response, authentication);
        return new CommonResponseDto(true, SuccessMessage.LOGOUT);
    }

    @Override
    public User register(RegisterRequestDto newUser) {

        boolean isUsernameExist = userRepository.existsAllByUsername(newUser.getUsername());
        boolean isEmailExist = userRepository.existsByEmail(newUser.getEmail());

        if (isUsernameExist) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_USERNAME);
        } else if (isEmailExist) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_EMAIL);
        } else {
            if (!newUser.getPassword().equals(newUser.getRepeatPassword())) {
                throw new InvalidException(ErrorMessage.INVALID_REPEAT_PASSWORD);
            } else {
                User user = userMapper.toUser(newUser);
                user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                user.setRole(roleRepository.findByRoleName(RoleConstant.USER));
                user.setCustomer(customerService.createCustomer(new CustomerDto(newUser.getUsername(), null, null, null)));
                cartService.createCartForCustomer(new CartDto(user.getCustomer().getId()));

                DataMailDto mailDto = new DataMailDto();
                mailDto.setTo(newUser.getEmail());
                mailDto.setSubject("Đăng ký thành công");
                Map<String, Object> properties = new HashMap<>();
                properties.put("username", newUser.getUsername());
                properties.put("password", newUser.getPassword());

                mailDto.setProperties(properties);

                try {
                    sendMailUtil.sendEmailWithHTML(mailDto, "register.html");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return userRepository.save(user);
            }
        }

    }

    @Override
    public CommonResponseDto forgetPassword(ForgetPasswordRequestDto requestDto) {

        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
        if (user.isEmpty()) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
        } else {
            if (!user.get().getEmail().equals(requestDto.getEmail())) {
                throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_EMAIL);
            } else {
                String newPassword = RandomPasswordUtil.random();

                user.get().setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user.get());

                DataMailDto mailDto = new DataMailDto();
                mailDto.setTo(requestDto.getEmail());
                mailDto.setSubject("Lấy lại mật khẩu");

                Map<String, Object> properties = new HashMap<>();
                properties.put("username", requestDto.getUsername());
                properties.put("newPassword", newPassword);

                mailDto.setProperties(properties);

                try {
                    sendMailUtil.sendEmailWithHTML(mailDto, "forgetPassword.html");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new CommonResponseDto(true, SuccessMessage.SEND_PASSWORD);
            }
        }
    }

    @Override
    public CommonResponseDto changPassword(ChangePasswordRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                        new String[]{username}));
        boolean isCorrectPassword = passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword());
        if (!isCorrectPassword) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
        } else {
            if (!requestDto.getPassword().equals(requestDto.getRepeatPassword())) {
                throw new InvalidException(ErrorMessage.INVALID_REPEAT_PASSWORD);
            }
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            userRepository.save(user);
            return new CommonResponseDto(true, SuccessMessage.CHANGE_PASSWORD);
        }
    }

    @Override
    public CommonResponseDto adminRegister(AdminRegisterRequestDto requestDto) {
        boolean isUsernameExist = userRepository.existsAllByUsername(requestDto.getUsername());
        boolean isEmailExist = userRepository.existsByEmail(requestDto.getEmail());

        if (isUsernameExist) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_USERNAME);
        } else if (isEmailExist) {
            throw new DataIntegrityViolationException(ErrorMessage.Auth.ERR_DUPLICATE_EMAIL);
        } else {
            if (!requestDto.getPassword().equals(requestDto.getRepeatPassword())) {
                throw new InvalidException(ErrorMessage.INVALID_REPEAT_PASSWORD);
            } else {
                User user = new User();
                user.setUsername(requestDto.getUsername());
                user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
                user.setRole(roleRepository.findByRoleName(requestDto.getRoleName()));
                if (requestDto.getRoleName().equals(RoleConstant.USER)) {
                    user.setCustomer(customerService.createCustomer(new CustomerDto(requestDto.getUsername(), requestDto.getPhoneNumber(), requestDto.getAddress(), requestDto.getAvatar())));

                }
                else{
                    user.setCustomer(customerService.createCustomer(new CustomerDto(requestDto.getUsername(), null,null,null)));

                }
                cartService.createCartForCustomer(new CartDto(user.getCustomer().getId()));

                DataMailDto mailDto = new DataMailDto();
                mailDto.setTo(requestDto.getEmail());
                mailDto.setSubject("Đăng ký thành công");
                Map<String, Object> properties = new HashMap<>();
                properties.put("username", requestDto.getUsername());
                properties.put("password", requestDto.getPassword());

                mailDto.setProperties(properties);

                try {
                    sendMailUtil.sendEmailWithHTML(mailDto, "register.html");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userRepository.save(user);
                return new CommonResponseDto(true,SuccessMessage.CREATE);
            }
        }
    }

}
