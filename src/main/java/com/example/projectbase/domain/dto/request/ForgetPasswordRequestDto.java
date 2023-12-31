package com.example.projectbase.domain.dto.request;

import com.example.projectbase.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ForgetPasswordRequestDto {
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String username;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String email;
}
