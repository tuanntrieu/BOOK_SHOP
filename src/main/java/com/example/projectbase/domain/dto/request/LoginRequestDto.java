package com.example.projectbase.domain.dto.request;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequestDto {

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String emailOrUsername;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String password;

}
