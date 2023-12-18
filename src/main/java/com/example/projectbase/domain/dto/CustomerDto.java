package com.example.projectbase.domain.dto;


import com.example.projectbase.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?:\\+84|0)(?:1[2689]|9[0-9]|3[2-9]|5[6-9]|7[0-9])(?:\\d{7}|\\d{8})$", message = ErrorMessage.INVALID_FORMAT_PHONE)
    private String phonenumber;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String address;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String avatar;
}
