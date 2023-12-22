package com.example.projectbase.domain.dto.request;

import com.example.projectbase.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindProductsAdminRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String name;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String categoryName;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int startQuantity;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int endQuantity;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int startSelled;

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private int endSelled;


}
